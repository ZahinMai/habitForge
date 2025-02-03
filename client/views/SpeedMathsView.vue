<template>
    <!--Overall container as template can contain only one root element-->
    <div class="container">
        
        <!-- Initial message before game starts -->
        <div class="captcha" v-if="!(gameStarted || gameOver || isLoading)">
            <input type="checkbox" id="robotCheck" v-model="isRobot" @change="startGame()"/>
            <span>I'm a robot</span>
            <img src="https://www.gstatic.com/recaptcha/api2/logo_48.png" alt="captcha icon" class="captcha-icon"/>
        </div>
        <!-- Loading Dialog -->
        <div v-if="isLoading" class="loading-dialog">
            <p>Loading CAPTCHA...</p>
        </div>
  
      <!-- Game container -->
      <div v-if="gameStarted" class="content-box">
        <form @submit.prevent="submitAnswer">
          <p class="captcha-prompt">Sum up these values:</p>
          <div class="captcha-problem">
            <span id="number1">{{ num1 }}</span> 
            <span id="plus"> + </span> 
            <span id="number2">{{ num2 }}</span>
          </div>
          <input 
            type="text" 
            id="answer" 
            v-model="userAnswer" 
            class="ans-input" 
            required 
            :disabled="timeLeft <= 0"
          >
          <button 
            v-if="!gameOver" 
            class="captcha-button" 
            type="submit" 
            id="submit" 
            :disabled="timeLeft <= 0"
          >
            Submit
          </button>
          <p v-if="gameOver" class="error">{{ gameOverMessage }}</p>
          <button 
            v-if="gameOver" 
            @click="resetGame" 
            class="captcha-button"
          >
            Try Again
          </button>
          <input name="__RequestVerificationToken" type="hidden" :value="csrfToken">
        </form>
      </div>
    </div>
</template>
  
  <script>
  export default {
    name: 'SpeedMathsView',
    data() {
      return {
        num1: 0,
        num2: 0,
        userAnswer: '',
        csrfToken: '',
        gameStarted: false,
        gameOver: false,
        gameOverMessage: '',
        timeLeft: 0,
        timer: null,
        robotConfirmation: false, // Checkbox state
        isLoading: false
      };
    },
    methods: {
        startGame() {
            this.gameOver = false;
            this.isLoading = true;  // Show loading dialog

            // Set a delay (e.g., 1 second) before starting the game
            setTimeout(() => {
                this.gameStarted = true;
                this.generateNewChallenge();
                this.startTimer();
                this.isLoading = false;  // Hide loading dialog after the delay
            }, 1000);  // Delay in milliseconds (1000ms = 1 second)
        },
      generateNewChallenge() {
        this.num1 = this.generateRandomNumber();
        this.num2 = this.generateRandomNumber();
        this.csrfToken = this.generateCSRFToken();
        this.userAnswer = '';
      },
      generateRandomNumber() {
        return Math.floor(Math.random() * 99) + 1;
      },
      generateCSRFToken() {
        return 'CfDJ8NSoxftjD7RHtkY_iiCJkhYow3kOXQhjObh2xvtn6ZlmMP5ybBhvOEODj2Rg5Qkedb5XMWDR03UUm9RZ65AAvLsF2lubWFYUohKes3fWxaWK3i74YOBaogeYbHysbYnJfs_gfoUuRRDng5wN-C2agjE';
      },
      startTimer() {
        this.timeLeft = 1000; // 1 second
        this.timer = setInterval(() => {
          this.timeLeft -= 10;
          if (this.timeLeft <= 0) {
            this.endGame('Too slow! You did not submit in time.');
          }
        }, 10);
      },
      submitAnswer() {
        clearInterval(this.timer);
        const expectedAnswer = this.num1 + this.num2;
        const userAnswer = parseInt(this.userAnswer);
  
        if (userAnswer === expectedAnswer) {
          this.$router.push('/admin');
        } else {
          this.endGame('Incorrect answer!');
        }
      },
      endGame(message) {
        clearInterval(this.timer);
        this.gameOver = true;
        this.gameOverMessage = message;
      },
      resetGame() {
        this.gameOver = false;
        this.generateNewChallenge();
        this.startTimer();
      },
    },
  };
  </script>
  
<style scoped>
/* Overall container */
    .container{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .captcha {
        display: flex;
        align-self: center;
        align-items: center;
        justify-self: center;
        justify-content: center;
        padding: 15px;
        background-color: #f7f7f7;
        border: 1px solid #bbb;
        border-radius: 5px;
        width: 100%;
        max-width: 400px;
        margin: 0 auto;
    }
    .captcha input[type="checkbox"] {
        width: 30px;
        height: 30px;
        margin-right: 15px;
    }
    .captcha span {
        font-size: 1.2rem;
        margin-right: 15px;
    }
    .captcha-icon {
        width: 50px;
        height: 50px;
        margin-left: auto; /* This will push the logo to the far right */
    }
    .loading-dialog {
        width: 50%;
        height: 50vh;
        display: flex;
        align-self: center;
        align-items: center;
        justify-self: center;
        justify-content: center;
        background-color: white;
        font-size: 1.5rem;
        z-index: 9999
    }

    .content-box {
        display: flex;
        flex-direction: column;
        align-self: center;
        align-items: center;
        text-align: center;
        justify-self: center;
        font-size: 2rem;
        justify-content: center;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        padding: 20px;
        width: 60%;
        height: 80%;
    }
  
    /* Input field */
    .ans-input {
        width: 90%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
        text-align: center;
        font-size: 16px;
        margin-bottom: 15px;
    }
  
    /* Buttons */
    button {
        width: 100%;
        padding: 10px;
        background-color: #4285f4;
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 14px;
        cursor: pointer;
        max-width: 300px;
    }
  
    button:hover {
        background-color: #357ae8;
    }
  
  /* Error message */
    .error {
        color: red;
        font-size: 14px;
        margin: 10px 0;
    }
  </style>
  