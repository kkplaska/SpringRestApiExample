// API Base URL
const API_URL = location.protocol + '//' + location.host + location.pathname + 'data';

// Patient Management
function showPatientForm() {
    const form = document.getElementById('patientForm');
    const table = document.getElementById('patientsList');

    if (form.style.display === 'none' || !form.style.display) {
        form.style.display = 'block';
        table.style.display = 'none';
    } else {
        form.style.display = 'none';
        table.style.display = 'block';
    }
}

async function loadPatients() {
    const response = await fetch(`${API_URL}/patients`);
    const patients = await response.json();
    const patientsList = document.getElementById('patientsList');
    populatePatientSelect(patients);

    patientsList.innerHTML = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Contact</th>
                </tr>
            </thead>
            <tbody>
                ${patients.map(patient => `
                    <tr>
                        <td>${patient.patientId}</td>
                        <td>${patient.firstName} ${patient.lastName}</td>
                        <td>${patient.email}</td>
                        <td>${patient.contactNumber}</td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

async function addPatient() {
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const contactNumber = document.getElementById('contactNumber').value;

    const response = await fetch(`${API_URL}/patients`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName,
            lastName,
            email,
            contactNumber
        })
    });

    if (response.ok) {
        document.getElementById('patientForm').style.display = 'none';
        document.getElementById('patientFormSubmit').reset();
    }
}

function populatePatientSelect(patients) {
    const patientSelect = document.getElementById('patientId');
    patientSelect.innerHTML = '<option value="">Select Patient</option>';

    patients.forEach(patient => {
        const option = document.createElement('option');
        option.value = patient.patientId;
        option.textContent = `${patient.patientId}.${patient.firstName} ${patient.lastName}`;
        patientSelect.appendChild(option);
    });
}

// Doctor Management
function showDoctorForm() {
    const form = document.getElementById('doctorForm');
    const table = document.getElementById('doctorsList');

    if (form.style.display === 'none' || !form.style.display) {
        form.style.display = 'block';
        table.style.display = 'none';
    } else {
        form.style.display = 'none';
        table.style.display = 'block';
    }
}

async function loadDoctors() {
    const response = await fetch(`${API_URL}/doctors`);
    const doctors = await response.json();
    const doctorsList = document.getElementById('doctorsList');
    populateDoctorSelect(doctors);

    doctorsList.innerHTML = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Specialization</th>
                </tr>
            </thead>
            <tbody>
                ${doctors.map(doctor => `
                    <tr>
                        <td>${doctor.doctorId}</td>
                        <td>${doctor.doctorName}</td>
                        <td>${doctor.specialization}</td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

async function addDoctor() {
    const doctorName = document.getElementById('doctorName').value;
    const specialization = document.getElementById('specialization').value;

    const response = await fetch(`${API_URL}/doctors`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            doctorName,
            specialization
        })
    });

    if (response.ok) {
        document.getElementById('doctorForm').style.display = 'none';
        document.getElementById('doctorFormSubmit').reset();
    }
}

function populateDoctorSelect(doctors) {
    const doctorSelect = document.getElementById('doctorId');
    doctorSelect.innerHTML = '<option value="">Select Doctor</option>';

    doctors.forEach(doctor => {
        const option = document.createElement('option');
        option.value = doctor.doctorId;
        option.textContent = `${doctor.doctorId}.${doctor.specialization} ${doctor.doctorName}`;
        doctorSelect.appendChild(option);
    });
}

// Visit Management
function showVisitForm() {
    const form = document.getElementById('visitForm');
    const table = document.getElementById('visitsList');

    if (form.style.display === 'none' || !form.style.display) {
        form.style.display = 'block';
        table.style.display = 'none';
    } else {
        form.style.display = 'none';
        table.style.display = 'block';
    }
}

async function loadVisits() {
    const response = await fetch(`${API_URL}/visits`);
    const visits = await response.json();
    const visitsList = document.getElementById('visitsList');

    visitsList.innerHTML = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Doctor</th>
                    <th>Patient</th>
                    <th>Scheduled Time</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                ${visits.map(visit => `
                    <tr>
                        <td>${visit.visitId}</td>
                        <td>${visit.doctor.doctorName}</td>
                        <td>${visit.patient.firstName} ${visit.patient.lastName}</td>
                        <td>${new Date(visit.scheduledTime).toLocaleString()}</td>
                        <td>${visit.status}</td>
                        <td>
                            <button onclick="cancelVisit(${visit.visitId})">Cancel</button>
                            <button onclick="deleteVisit(${visit.visitId})">Delete</button>
                        </td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

async function addVisit() {
    const doctorId = document.getElementById('doctorId').value;
    const patientId = document.getElementById('patientId').value;
    const scheduledTime = document.getElementById('scheduledTime').value;

    // Validate required fields
    if (!patientId || !doctorId || !scheduledTime) {
        alert('Please fill in all required fields');
        return;
    }

    const response = await fetch(`${API_URL}/visits`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            doctorId: parseInt(doctorId),
            patientId: parseInt(patientId),
            scheduledTime
        })
    });

    if (response.ok) {
        document.getElementById('visitForm').style.display = 'none';
        document.getElementById('visitFormSubmit').reset();
    } else {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    alert('Visit created successfully!');
}

async function cancelVisit(visitId) {
    if (!confirm(`Are you sure you want to cancel visit with ID ${visitId}?`)) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/visits/${visitId}/status?status=CANCELED`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        console.log(response)

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Update visits list
        await loadVisits();

        alert('Visit canceled successfully!');
    } catch (error) {
        console.error('Error canceling visit:', error);
        alert('Failed to cancel visit. Please try again.');
    }
}

async function deleteVisit(visitId) {
    if (!confirm(`Are you sure you want to delete visit with ID ${visitId}?`)) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/visits/${visitId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        console.log(response)

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Update visits list
        await loadVisits();

        alert('Visit deleted successfully!');
    } catch (error) {
        console.error('Error deleting visit:', error);
        alert('Failed to delete visit. Please try again.');
    }
}

// Event Listeners
document.getElementById('patientFormSubmit')?.addEventListener('submit', handlePatientFormSubmit);
document.getElementById('doctorFormSubmit')?.addEventListener('submit', handleDoctorFormSubmit);
document.getElementById('visitFormSubmit')?.addEventListener('submit', handleVisitFormSubmit);

// Handlers
async function handlePatientFormSubmit(event) {
    event.preventDefault();
    try {
        addPatient().then(r => location.reload());
    } catch (error) {
        console.error('Error adding patient:', error);
        alert('Failed to add patient. Please try again.');
    }
}

async function handleDoctorFormSubmit(event) {
    event.preventDefault();
    try {
        addDoctor().then(r => location.reload());
    } catch (error) {
        console.error('Error adding doctor:', error);
        alert('Failed to add doctor. Please try again.');
    }
}

async function handleVisitFormSubmit(event) {
    event.preventDefault(); // Prevent default form submission
    try {
        addVisit().then(r => location.reload())
    } catch (error) {
        console.error('Error creating visit:', error);
        alert('Failed to create visit. Please try again.');
    }
}

// Initialize
loadPatients();
loadDoctors();
loadVisits();