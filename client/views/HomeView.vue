<!-- src/views/HomeView.vue -->
<template>
  <div class="home">
    <h1>Welcome to the HabitForge App</h1>
    <p>Start building your habits today!</p>
    <div class="illustration">
      <!-- Space for a vector illustration or image -->
      <img src="../assets/turbines.png" alt="Nature Illustration">
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
};
</script>

<style scoped>
.home {
  text-align: center;
  padding: 20px;
}

.illustration {
  margin-top: 20px;
  width: 100%;
  max-width: 500px;
  margin: 0 auto;
}

img {
  width: 100%;
  height: auto;
}
</style>
