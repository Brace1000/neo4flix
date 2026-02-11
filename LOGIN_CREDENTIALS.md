# 🔐 Neo4flix Login Credentials

## Existing Account (Already Created)

**Username:** `testuser3`  
**Password:** `SecurePass123!`

This account already exists and was tested via API.

---

## Create New Account

### Option 1: Use the Frontend

1. Go to http://localhost:4200
2. Click **"Sign Up"** button
3. Fill in the form:
   - **Username:** Choose any username (e.g., `john_doe`, `moviefan`, `yourname`)
   - **Email:** Any email format (e.g., `john@example.com`, `test@test.com`)
   - **Password:** Must meet requirements (see below)
4. Click **"Sign Up"**
5. You'll be automatically logged in!

### Option 2: Create via Terminal

```bash
curl -X POST http://localhost:8081/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "moviefan",
    "email": "moviefan@example.com",
    "password": "MyPassword123!"
  }'
```

---

## Password Requirements

Your password MUST have:
- ✅ At least **8 characters**
- ✅ One **uppercase** letter (A-Z)
- ✅ One **lowercase** letter (a-z)
- ✅ One **number** (0-9)
- ✅ One **special character** from: `@#$%^&+=!`

### ✅ Valid Password Examples:
- `SecurePass123!`
- `MyPassword123!`
- `Welcome2024!`
- `MovieFan123@`
- `Test1234!`

### ❌ Invalid Password Examples:
- `password` (no uppercase, no number, no special char)
- `Password` (no number, no special char)
- `Password123` (no special char)
- `pass!` (too short)

---

## Quick Test Accounts

Use any of these to sign up:

### Account 1:
- **Username:** `moviefan`
- **Email:** `moviefan@example.com`
- **Password:** `MovieFan123!`

### Account 2:
- **Username:** `filmcritic`
- **Email:** `critic@example.com`
- **Password:** `FilmCritic2024!`

### Account 3:
- **Username:** `cinephile`
- **Email:** `cinephile@example.com`
- **Password:** `CinemaLover123!`

---

## Step-by-Step: Sign Up & Login

### Sign Up (New User):

1. **Open:** http://localhost:4200
2. **Click:** "Sign Up" button (top right)
3. **Enter:**
   - Username: `moviefan`
   - Email: `moviefan@example.com`
   - Password: `MovieFan123!`
4. **Click:** "Sign Up"
5. **Success!** You're now logged in

### Login (Existing User):

1. **Open:** http://localhost:4200
2. **Click:** "Login" button
3. **Enter:**
   - Username: `testuser3`
   - Password: `SecurePass123!`
4. **Click:** "Login"
5. **Success!** You're logged in

---

## What to Do After Login

1. ✅ **Search for movies** - Type any movie name
2. ✅ **Click on a movie** - View details
3. ✅ **Add to Watchlist** - Click the button (CONFIRMED WORKING!)
4. ✅ **Rate the movie** - Click stars (1-5)
5. ✅ **Go to Watchlist** - See your saved movies
6. ✅ **Browse recommendations** - Get suggestions

---

## Troubleshooting

### "Registration failed" error?
- Check password meets ALL requirements
- Try a different username (might be taken)
- Make sure all fields are filled

### "Invalid credentials" on login?
- Check username spelling
- Check password (case-sensitive!)
- Try the test account: `testuser3` / `SecurePass123!`

### Can't access http://localhost:4200?
```bash
# Check if services are running
sudo docker compose ps

# Restart if needed
sudo docker compose restart frontend
```

---

## Ready to Test!

**Use this account right now:**

```
Username: testuser3
Password: SecurePass123!
```

**Or create your own with any username and this password format:**
```
YourName123!
```

**Go to:** http://localhost:4200

**Enjoy Neo4flix!** 🎬
