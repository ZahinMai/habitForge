// server/server.js
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const dotenv = require('dotenv');
dotenv.config();
const gameRoutes = require('./routes/gameRoutes');
const csrfProtection = require('./middleware/csrfProtection');
const app = express();
const port = 5000;

// MongoDB connection string
const MONGODB_URI = "mongodb+srv://zahin08:Kittykat08@cluster0.wct4e.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

// Middleware
app.use(cors());
app.use(express.json());
app.use(csrfProtection); // CSRF protection for all routes
app.use('/api', gameRoutes);

// Database connection
mongoose.connect(MONGODB_URI, {
useNewUrlParser: true,
useUnifiedTopology: true,
serverSelectionTimeoutMS: 5000,  // Timeout for initial connection attempt
socketTimeoutMS: 45000,          // Socket timeout to prevent disconnections
reconnectTries: Number.MAX_VALUE,  // Infinite reconnection attempts
reconnectInterval: 1000,           // Reconnection interval in ms
autoReconnect: true,                // Automatically reconnect
}).then(() => {
console.log('MongoDB connected');
}).catch((err) => {
console.error('Database connection error:', err);
});
// Sample route
app.get('/', (req, res) => {
  res.send('Hello from the server');
});

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});

