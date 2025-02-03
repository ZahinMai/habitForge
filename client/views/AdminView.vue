<template>
    <div class="admin-dashboard">
      <div class="card">
        <div class="card-header">
          <h2>User Management</h2>
          <button @click="openAddUserModal" class="btn btn-primary">
            Add New User
          </button>
        </div>
  
        <table class="table">
          <thead>
            <tr>
              <th>Username</th>
              <th>Email</th>
              <th>Challenges in Progress</th>
              <th>Habits Forged</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.totalHabits }}</td>
              <td>{{ user.completedHabits }}</td>
              <td>
                <div class="action-buttons">
                  <button 
                    @click="editUser(user)" 
                    class="btn btn-edit"
                  >
                    Edit
                  </button>
                  <button 
                    @click="deleteUser(user.id)" 
                    class="btn btn-delete"
                  >
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <!-- User Modal -->
      <div v-if="selectedUser" class="modal-overlay">
        <div class="modal">
          <div class="modal-header">
            <h3>{{ isNewUser ? 'Add User' : 'Edit User' }}</h3>
            <button @click="closeModal" class="close-btn">&times;</button>
          </div>
          
            <div class="modal-body">
                <div class="form-group">
                    <label>Username</label>
                    <input v-model="selectedUser.username" type="text" class="form-control">
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input v-model="selectedUser.email"  type="email"  class="form-control">
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input v-model="selectedUser.password"  type="password"  class="form-control">
                </div>
            </div>

          <div class="modal-footer">
            <button @click="closeModal" class="btn btn-secondary">Cancel</button>
            <button @click="saveUser" class="btn btn-primary"> Save</button>
          </div>

        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        users: [
          { 
            id: 1, 
            username: 'johndoe', 
            email: 'john@example.com', 
            password: 'janedoe',
            totalHabits: 5, 
            completedHabits: 3 
          },
          { 
            id: 2, 
            username: 'janedoe', 
            email: 'jane@example.com',
            password: 'janedoe',
            totalHabits: 7, 
            completedHabits: 6 
          }
        ],
        selectedUser: null,
        isNewUser: false
      }
    },
    methods: {
      editUser(user) {
        this.selectedUser = { ...user };
        this.isNewUser = false;
      },
      deleteUser(userId) {
        this.users = this.users.filter(user => user.id !== userId);
      },
      openAddUserModal() {
        this.selectedUser = {
          id: this.users.length + 1,
          username: '',
          email: '',
          totalHabits: 0,
          completedHabits: 0
        };
        this.isNewUser = true;
      },
      closeModal() {
        this.selectedUser = null;
      },
      saveUser() {
        if (this.isNewUser) {
          this.users.push(this.selectedUser);
        } else {
          const index = this.users.findIndex(u => u.id === this.selectedUser.id);
          if (index !== -1) {
            this.$set(this.users, index, this.selectedUser);
          }
        }
        this.closeModal();
      }
    }
  }
  </script>
  
  <style scoped>

  .admin-dashboard {
    font-family: Arial, sans-serif;
    width: 100%;
    padding: 20px;
  }
  
  .card {
    margin: 0 auto;
    max-width: 1000px;
    background-color: #fff;
    border-radius: 5px;
    border:  rgba(0,0,0,0.3) 0.5px solid;
    box-shadow: 0 3px 6px rgba(0,0,0,0.3);
  }
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    border-bottom: 1px solid #eee;
  }
  
  .card-header h2 {
    margin: 0;
    font-size: 1.2em;
  }
  
  .table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .table th, .table td {
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #eee;
  }
  
  .table th {
    background-color: #f4f4f4;
    font-weight: bold;
  }
  
  .action-buttons {
    display: flex;
    gap: 10px;
  }
  
  .btn {
    padding: 6px 12px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.2s;
  }
  
  .btn-primary {
    background-color: #007bff;
    color: white;
  }
  
  .btn-edit {
    background-color: #28a745;
    color: white;
  }
  
  .btn-delete {
    background-color: #dc3545;
    color: white;
  }
  
  .btn-secondary {
    background-color: #6c757d;
    color: white;
  }
  
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
  }
  
  .modal {
    background-color: white;
    border-radius: 5px;
    width: 500px;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  }
  
  .modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    border-bottom: 1px solid #eee;
  }
  
  .close-btn {
    background: none;
    border: none;
    font-size: 1.5em;
    cursor: pointer;
  }
  
  .modal-body {
    padding: 5%;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 5px;
  }
  
  .form-control {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
  .modal-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    padding: 15px;
    border-top: 1px solid #eee;
  }
  </style>