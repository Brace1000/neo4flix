/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#e50914',
        secondary: '#221f1f',
        dark: '#141414',
      }
    },
  },
  plugins: [],
}
