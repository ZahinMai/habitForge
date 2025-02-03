<template>
  <div class="register-container">
    <!-- Banner Section -->
    <div class="register-banner">
      <div class="banner-content">
        <img id="banner-img" src="../assets/camping.png" alt="Camping">
        <h1>Forge Habits with HabitForge</h1>
        <p>Forge a New Lifestyle, Fuel a Greener Tomorrow - One Habit At a Time</p>
      </div>
    </div>

    <!-- Form Section -->
    <div class="register-form">
      <div class="form-wrapper">
        <h2 class="register-title">Welcome to HabitForge</h2>
        <p class="register-subtitle">Sign in to transform your habits</p>

        <form @submit.prevent="handleregister">
          <!-- Email Input -->
          <div class="input-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" v-model="email" placeholder="Enter your email" required />

          <!-- Password Input -->
            <label for="password">Password</label>
            <input type="password" id="password" v-model="password" placeholder="Enter your password" required />
          
            <button type="submit" class="register-button">Sign In</button>
            <div class="form-footer">
              <p>Already a HabitForge user? <a href="/">Log In</a></p>
            </div>
          </div>

          <!-- Submit Button and Footer -->
          <div class="form-actions">
            
          </div>
        </form>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: 'registerView',
  data() {
    return {
      email: '',
      password: '',
      loading: false,
      error: null
    }
  },
  methods: {
    async handleregister() {
      this.loading = true
      this.error = null

      try {
        await this.$store.dispatch('register', {
          email: this.email,
          password: this.password
        })

        // Redirect to home page after successful register
        this.$router.push('/home')
      } catch (error) {
        this.error = this.$store.state.authError || 'register failed'
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  height: 100vh;
}

.register-form {
  width: 65%;
  background-color: white;
  padding: 2rem;
  margin-left: 8%;
}
.register-banner {
  width: 35%;
  background: #BAB653;
  color: white;
  text-align: center;
  justify-content: center;
}
.register-banner, .register-form{
  display: flex;
  align-items: center;
}
.register-title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #666538;
  margin-bottom: 0.5rem;
}
.register-subtitle {
  color: #7f8c8d;
  margin-bottom: 2rem;
}

.banner-content img {
  width: 100%;
  margin-bottom: 1rem;
}

.form-wrapper {
  max-width: 400px;
  width: 100%;
}

.input-group label {
  display: block;
  margin-bottom: 0.5rem;
}

.input-group input {
  width: 95%;
  padding: 0.5rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.register-button {
  width: 100%;
  padding: 0.75rem;
  background: #BAB653;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin: 0 auto !important;
}

.form-footer {
  margin-top: 1rem;
}

.form-footer a {
  color: #3498db;
  text-decoration: none;
}
</style>
