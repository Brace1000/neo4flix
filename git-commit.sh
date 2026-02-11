#!/bin/bash

# Automated Git Commit Script
# Usage: ./git-commit.sh

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}🚀 Automated Git Commit Script${NC}"
echo "================================"
echo ""

# Check if we're in a git repository
if ! git rev-parse --git-dir > /dev/null 2>&1; then
    echo -e "${RED}❌ Error: Not a git repository${NC}"
    echo "Run 'git init' first or navigate to a git repository"
    exit 1
fi

# Check for changes
if git diff-index --quiet HEAD --; then
    echo -e "${RED}ℹ️  No changes to commit${NC}"
    exit 0
fi

# Show status
echo -e "${BLUE}📊 Current Status:${NC}"
git status --short
echo ""

# Stage all changes
echo -e "${BLUE}📦 Staging all changes...${NC}"
git add .

# Generate commit message based on changes
echo -e "${BLUE}💬 Generating commit message...${NC}"

# Get changed files
CHANGED_FILES=$(git diff --cached --name-only | wc -l)
ADDED_FILES=$(git diff --cached --diff-filter=A --name-only | wc -l)
MODIFIED_FILES=$(git diff --cached --diff-filter=M --name-only | wc -l)
DELETED_FILES=$(git diff --cached --diff-filter=D --name-only | wc -l)

# Get main file types changed
FILE_TYPES=$(git diff --cached --name-only | sed 's/.*\.//' | sort | uniq | head -3 | tr '\n' ', ' | sed 's/,$//')

# Generate smart commit message
TIMESTAMP=$(date +"%Y-%m-%d %H:%M")

if [ $ADDED_FILES -gt 0 ] && [ $MODIFIED_FILES -eq 0 ] && [ $DELETED_FILES -eq 0 ]; then
    COMMIT_MSG="feat: Add new files ($ADDED_FILES files) - $TIMESTAMP"
elif [ $MODIFIED_FILES -gt 0 ] && [ $ADDED_FILES -eq 0 ] && [ $DELETED_FILES -eq 0 ]; then
    COMMIT_MSG="update: Modify existing files ($MODIFIED_FILES files) - $TIMESTAMP"
elif [ $DELETED_FILES -gt 0 ]; then
    COMMIT_MSG="chore: Remove files ($DELETED_FILES deleted) - $TIMESTAMP"
else
    COMMIT_MSG="chore: Update project ($CHANGED_FILES files changed) - $TIMESTAMP"
fi

# Add file types to message if available
if [ ! -z "$FILE_TYPES" ]; then
    COMMIT_MSG="$COMMIT_MSG [$FILE_TYPES]"
fi

echo -e "${GREEN}Message: $COMMIT_MSG${NC}"
echo ""

# Commit changes
echo -e "${BLUE}✅ Committing changes...${NC}"
git commit -m "$COMMIT_MSG"

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Commit successful!${NC}"
    echo ""
    
    # Ask to push
    echo -e "${BLUE}🚀 Ready to push to remote?${NC}"
    echo "Press Enter to push, or Ctrl+C to cancel..."
    read
    
    # Get current branch
    BRANCH=$(git rev-parse --abbrev-ref HEAD)
    
    echo -e "${BLUE}📤 Pushing to origin/$BRANCH...${NC}"
    git push origin $BRANCH
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Push successful!${NC}"
        echo ""
        echo -e "${GREEN}🎉 All done!${NC}"
    else
        echo -e "${RED}❌ Push failed${NC}"
        echo "You may need to set up remote or pull first"
        echo "Run: git push -u origin $BRANCH"
    fi
else
    echo -e "${RED}❌ Commit failed${NC}"
    exit 1
fi
