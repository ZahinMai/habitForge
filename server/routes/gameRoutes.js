const express = require('express');
const router = express.Router();
const gameController = require('../controllers/gameController');

router.get('/csrf-token', gameController.getCsrfToken);
router.post('/submit-answer', gameController.submitAnswer);

module.exports = router;

