export default {
  async fetchCsrfToken() {
    const response = await fetch('/api/csrf-token');
    const data = await response.json();
    return data.csrfToken;
  },
  async submitAnswer(answer, csrfToken) {
    const response = await fetch('/api/submit-answer', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ answer, csrfToken })
    });
    return await response.json();
  }
};
