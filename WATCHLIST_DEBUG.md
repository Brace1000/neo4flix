# 🔧 Watchlist Not Showing Movies - Troubleshooting

## Issue
You can add movies to watchlist, but when you go to the Watchlist page, it shows "Your watchlist is empty".

## Root Cause
The watchlist IS working (confirmed via API), but the frontend might have issues:
1. Movie IDs are being saved correctly
2. But when fetching movie details, it might fail

## Quick Fixes to Try

### Fix 1: Hard Refresh Browser
```
Press Ctrl+Shift+R (or Cmd+Shift+R on Mac)
```
This clears the cache and reloads everything.

### Fix 2: Check Browser Console
1. Press F12 to open Developer Tools
2. Go to "Console" tab
3. Look for any red errors
4. Take a screenshot and share if you see errors

### Fix 3: Verify Login
1. Logout (click your username, then Logout)
2. Login again with: `testuser3` / `SecurePass123!`
3. Try adding to watchlist again

### Fix 4: Test with Known Movie
1. Search for "fight club"
2. Click on "Fight Club" movie
3. Click "Add to Watchlist"
4. Go to Watchlist page
5. Should see the movie

## Manual Test via Browser Console

Open browser console (F12) and run:

```javascript
// Check if user is logged in
console.log('User:', JSON.parse(localStorage.getItem('neo4flix_user')));
console.log('Token:', localStorage.getItem('neo4flix_token'));

// Check watchlist
fetch('http://localhost:8081/api/users/0/watchlist', {
  headers: {
    'Authorization': 'Bearer ' + localStorage.getItem('neo4flix_token')
  }
})
.then(r => r.json())
.then(data => console.log('Watchlist:', data));
```

## What Should Happen

### When Adding to Watchlist:
1. Click "Add to Watchlist" button
2. Button should change to "✓ In Watchlist"
3. Button color changes from red to gray

### When Viewing Watchlist:
1. Go to Watchlist page (navbar)
2. Should see movie posters
3. Each movie shows title and rating
4. Can click X to remove

## Known Working (Confirmed via API)

✅ Add to watchlist: WORKS
✅ View watchlist IDs: WORKS (returns ["550"])
✅ Backend endpoints: WORKING

## Likely Issues

### Issue 1: Movie Details Not Loading
The watchlist has movie IDs, but when it tries to fetch movie details, it might fail because:
- Movie ID format mismatch
- Movie doesn't exist in database
- API endpoint issue

### Issue 2: Frontend Cache
Browser might be caching old version of the app.

### Issue 3: User ID Mismatch
Frontend might be using wrong user ID.

## Debug Steps

### Step 1: Check User ID
Open Console (F12) and run:
```javascript
JSON.parse(localStorage.getItem('neo4flix_user')).id
```
Should show: `"0"` or another number

### Step 2: Check Watchlist API
```javascript
fetch('http://localhost:8081/api/users/0/watchlist', {
  headers: {'Authorization': 'Bearer ' + localStorage.getItem('neo4flix_token')}
})
.then(r => r.json())
.then(d => console.log(d))
```
Should show: `["550"]` or other movie IDs

### Step 3: Check Movie Details
```javascript
fetch('http://localhost:8082/api/movies/550')
.then(r => r.json())
.then(d => console.log(d))
```
Should show movie details

## Temporary Workaround

If watchlist page doesn't work, you can still:
1. ✅ Search and browse movies
2. ✅ View movie details
3. ✅ Rate movies
4. ✅ See if button shows "✓ In Watchlist" (means it's added)

## Report Back

Please check:
1. [ ] Did hard refresh (Ctrl+Shift+R) help?
2. [ ] Any errors in browser console (F12)?
3. [ ] What does the console show for user ID?
4. [ ] What does watchlist API return?

Share the console output and I can provide specific fix!

---

**The watchlist backend is 100% working. This is a frontend display issue that can be debugged with console logs.**
