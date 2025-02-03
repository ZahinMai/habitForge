<template>
  <div class="login-container">
    <div class="login-form">
      <div class="form-wrapper">
        <h2 class="login-title">Welcome to HabitForge</h2>
        <p class="login-subtitle">Sign in to transform your habits</p>
        
        <form @submit.prevent="handleLogin" class="login-form-inputs">
          <div class="input-group">
            <label for="email">Email Address</label>
            <input 
              type="email" 
              id="email" 
              v-model="email" 
              required 
              placeholder="Enter your email"
            />
          </div>
          
          <div class="input-group">
            <label for="password">Password</label>
            <input 
              type="password" 
              id="password" 
              v-model="password" 
              required 
              placeholder="Enter your password"
            />
          </div>
          
  
          <button type="submit" class="primary-button-wide">
            Sign In
          </button>
          
          <div class="form-footer">
            <p>Don't have an account? <a href="/register">Register</a></p>
            <a href="/forgot-password" class="forgot-password">Forgot Password?</a>
          </div>
          
        </form>
      </div>
    </div>
    
    <div class="login-banner">
      <div class="banner-content">
        <img id="banner-img" src="../assets/earth.png">
        <h1>Forge Habits with HabitForge</h1>
        <p>Forge a New Lifestyle, Fuel a Greener Tomorrow - One Habit At a Time</p>
        <!--
        <ul class="banner-features">
          <li>🎯 Set Clear Goals</li>
          <li>📊 Track Progress</li>
          <li>🔄 Build Consistency</li>
          <li>🚀 Transform Your Life</li>
        </ul>
        -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      email: '',
      password: '',
      loading: false,
      error: null
    }
  },
  methods: {
    async handleLogin() {
      this.loading = true
      this.error = null

      try {
        await this.$store.dispatch('login', {
          email: this.email,
          password: this.password
        })

        // Redirect to home page after successful login
        this.$router.push('/home')
      } catch (error) {
        this.error = this.$store.state.authError || 'Login failed'
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.login-form {
  width: 35%;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}

.form-wrapper {
  width: 100%;
  max-width: 400px;
}

.login-title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #666538;
  margin-bottom: 0.5rem;
}

.login-subtitle {
  color: #7f8c8d;
  margin-bottom: 2rem;
}

.input-group {
  margin-bottom: 1.5rem;
}

.input-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #353519;
  font-weight: 500;
}

.input-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #bdc3c7;
  border-radius: 4px;
  transition: border-color 0.3s ease;
}

.input-group input:focus {
  outline: none;
  border-color: #3498db;
}

.primary-button-wide{
  width: 100%;
  padding: 0.75rem;
  background-color: #666538;
  color: #F4ECE5;
  border: none;
  border-radius: 30px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.secondary-button-wide{
  width: 100%;
  padding: 0.75rem;
  color: #666538;
  background-color: #F4ECE5;
  border: none;
  border-radius: 30px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.primary-button-wide:hover {
  background-color: #828257;
}
.form-footer {
  margin-top: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.form-footer a {
  color: #3498db;
  text-decoration: none;
}

.login-banner {
  width: 65%;
  background: #BAB653;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.banner-content {
  max-width: 600px;
  padding: 2rem;
}

.banner-content h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
}
#banner-img{
  width: 80%;
}
.banner-content p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  color: rgba(255, 255, 255, 0.9);
}

.banner-features {
  list-style-type: none;
  padding: 0;
  display: flex;
  justify-content: center;
  gap: 1rem;
}

.banner-features li {
  background-color: rgba(255, 255, 255, 0.1);
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  font-size: 1rem;
}
a{
  color: #F98638 !important;
}
</style>
