document.addEventListener('DOMContentLoaded', () => {
    const baseUrl = '/api/users';

    // Create User
    document.getElementById('create-user-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const name = document.getElementById('create-user-name').value;
        const lastName = document.getElementById('create-user-last-name').value;
        const email = document.getElementById('create-user-email').value;
        const password = document.getElementById('create-user-password').value;

        const response = await fetch(`${baseUrl}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name,
                lastName,
                email,
                password
            })
        });

        if (response.ok) {
            alert('User created successfully!');
            document.getElementById('create-user-form').reset();
        } else {
            alert('Error creating user.');
        }
    });

    // Give Admin Role
    document.getElementById('give-admin-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('give-admin-email').value;
        const response = await fetch(`${baseUrl}/giveAdmin/${email}`, {
            method: 'PUT',  // Changed to POST or adjust based on your backend implementation
        });

        if (response.ok) {
            alert('User role updated to Admin successfully!');
            document.getElementById('give-admin-form').reset();
        } else {
            alert('Error updating user role.');
        }
    });

    // Get User By Email
    document.getElementById('get-user-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('get-user-email').value;

        const response = await fetch(`${baseUrl}/get/${email}`);
        if (response.ok) {
            const user = await response.json();
            const formattedUser = `
                <div class="user-item">
                    <strong>Name:</strong> ${user.name}<br>
                    <strong>Last Name:</strong> ${user.last_name}<br>
                    <strong>Email:</strong> ${user.email}<br>
                    <strong>Status:</strong> ${user.accountStatus}<br>
                    <strong>Role:</strong> ${user.role}<br>
                </div>`;
            document.getElementById('user-details').innerHTML = formattedUser;
        } else {
            document.getElementById('user-details').textContent = 'Error retrieving user.';
        }
    });

    // List All Users
    document.getElementById('list-users-button').addEventListener('click', async () => {
        const response = await fetch(`${baseUrl}/get/all`);
        if (response.ok) {
            const users = await response.json();
            const formattedUsers = users.map(user =>
                `<div class="user-item">
                    <strong>Name:</strong> ${user.name}<br>
                    <strong>Last Name:</strong> ${user.last_name}<br>
                    <strong>Email:</strong> ${user.email}<br>
                    <strong>Status:</strong> ${user.accountStatus}<br>
                    <strong>Role:</strong> ${user.role}<br>
                </div>`
            ).join('<hr>');
            document.getElementById('users-list').innerHTML = formattedUsers;
        } else {
            alert('Error retrieving users.');
        }
    });

    // Delete User
    document.getElementById('delete-user-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('delete-user-email').value;

        const response = await fetch(`${baseUrl}/delete/${email}`, {
            method: 'DELETE',  // Changed to DELETE
        });

        if (response.ok) {
            alert('User deleted successfully!');
            document.getElementById('delete-user-form').reset();
        } else {
            alert('Error deleting user.');
        }
    });

    // Update User
    document.getElementById('update-user-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('update-user-email').value;
        const name = document.getElementById('update-user-name').value;
        const lastName = document.getElementById('update-user-last-name').value;
        const password = document.getElementById('update-user-password').value;

        const response = await fetch(`${baseUrl}/update/${email}`, {
            method: 'PUT',  // Changed to PUT
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name,
                last_name: lastName,
                password
                // Optionally include status and role if needed
            })
        });

        if (response.ok) {
            alert('User updated successfully!');
            document.getElementById('update-user-form').reset();
        } else {
            alert('Error updating user.');
        }
    });
});
