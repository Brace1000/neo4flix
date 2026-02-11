# 🚀 Automated Git Commit Script

## Quick Start

### Single Command:
```bash
./git-commit.sh
```

That's it! The script will:
1. ✅ Check for changes
2. ✅ Stage all files (`git add .`)
3. ✅ Generate smart commit message
4. ✅ Commit changes
5. ✅ Push to remote (after confirmation)

## Features

### Smart Commit Messages
The script automatically generates commit messages based on your changes:

- **New files added**: `feat: Add new files (5 files) - 2024-02-11 10:30 [py, js, md]`
- **Files modified**: `update: Modify existing files (3 files) - 2024-02-11 10:30 [java, ts]`
- **Files deleted**: `chore: Remove files (2 deleted) - 2024-02-11 10:30`
- **Mixed changes**: `chore: Update project (10 files changed) - 2024-02-11 10:30`

### What It Does

1. **Checks if you're in a git repo**
2. **Shows current status** (what files changed)
3. **Stages all changes** (`git add .`)
4. **Generates commit message** (smart, based on changes)
5. **Commits** with generated message
6. **Asks to push** (press Enter to push, Ctrl+C to cancel)
7. **Pushes to remote** (current branch)

## Usage Examples

### Basic Usage:
```bash
./git-commit.sh
```

### First Time Setup:
```bash
# Make it executable (only needed once)
chmod +x git-commit.sh

# Run it
./git-commit.sh
```

### Add to PATH (Optional):
```bash
# Copy to local bin
sudo cp git-commit.sh /usr/local/bin/git-commit

# Now use from anywhere
cd /any/git/repo
git-commit
```

## What You'll See

```
🚀 Automated Git Commit Script
================================

📊 Current Status:
M  backend/user-service/src/main/java/UserService.java
A  frontend/src/app/new-component.ts
D  old-file.txt

📦 Staging all changes...
💬 Generating commit message...
Message: chore: Update project (3 files changed) - 2024-02-11 10:30 [java, ts, txt]

✅ Committing changes...
✅ Commit successful!

🚀 Ready to push to remote?
Press Enter to push, or Ctrl+C to cancel...

📤 Pushing to origin/main...
✅ Push successful!

🎉 All done!
```

## Customization

### Edit Commit Message Format:
Open `git-commit.sh` and modify line 48-56:
```bash
COMMIT_MSG="your custom format here"
```

### Auto-push Without Confirmation:
Remove lines 68-70 (the read prompt)

### Change Branch:
The script auto-detects your current branch

## Troubleshooting

### "Not a git repository"
```bash
# Initialize git first
git init
git remote add origin YOUR_REPO_URL
```

### "Push failed"
```bash
# Set upstream branch first
git push -u origin main

# Then use the script
./git-commit.sh
```

### "Permission denied"
```bash
chmod +x git-commit.sh
```

## Advanced: Alias

Add to your `~/.bashrc` or `~/.zshrc`:
```bash
alias gc='./git-commit.sh'
```

Then just run:
```bash
gc
```

## Quick Reference

| Command | Description |
|---------|-------------|
| `./git-commit.sh` | Run the script |
| `chmod +x git-commit.sh` | Make executable |
| `git status` | Check changes manually |
| `git log` | View commit history |

---

**One command to commit and push everything!** 🚀
