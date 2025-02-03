const csrf = require('csurf');
const csrfProtection = csrf({ cookie: true });

module.exports = {
  getCsrfToken: (req, res) => {
    res.json({ csrfToken: req.csrfToken() });
  },
  submitAnswer: (req, res) => {
    const { answer, csrfToken } = req.body;
    if (parseInt(answer) === 7) {  // Example check
      res.json({ correct: true });
    } else {
      res.json({ correct: false });
    }
  }
};

