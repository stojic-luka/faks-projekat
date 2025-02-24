/** @type {import('tailwindcss').Config} */

const safelist = [];

export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        "accent-svg": "#BFBFBF",
      },
      animation: {
        "bigger-bounce": "bigger-bounce 900ms ease-in-out infinite",
      },
      keyframes: {
        "bigger-bounce": {
          "0%, 100%": {
            transform: "translateY(-75%)",
            "animation-timing-function": "cubic-bezier(0.5,0,0.8,1)",
          },
          "50%": {
            transform: "none",
            "animation-timing-function": "cubic-bezier(0,0,0.2,1)",
          },
        },
      },
    },
  },
  safelist: safelist,
  plugins: [],
};
