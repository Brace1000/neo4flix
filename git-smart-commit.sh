#!/bin/bash

# Smart Git Commit Script - Commits Each File Separately
# Usage: ./git-smart-commit.sh

GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}🚀 Smart Git Commit Script${NC}"
echo -e "${YELLOW}Commits each file separately with smart messages${NC}"
echo "=================================================="
echo ""

# Check if in git repo
if ! git rev-parse --git-dir > /dev/null 2>&1; then
    echo -e "${RED}❌ Not a git repository${NC}"
    exit 1
fi

# Get list of changed files
CHANGED_FILES=$(git diff --name-only 2>/dev/null)
UNTRACKED_FILES=$(git ls-files --others --exclude-standard 2>/dev/null)
ALL_FILES=$(echo -e "$CHANGED_FILES\n$UNTRACKED_FILES" | grep -v '^$')

if [ -z "$ALL_FILES" ]; then
    echo -e "${YELLOW}ℹ️  No changes to commit${NC}"
    exit 0
fi

# Count files
TOTAL_FILES=$(echo "$ALL_FILES" | wc -l | tr -d ' ')

echo -e "${BLUE}📊 Found $TOTAL_FILES file(s) to commit${NC}"
echo ""

# Function to generate smart commit message
generate_commit_message() {
    local file=$1
    local filename=$(basename "$file")
    local dir=$(dirname "$file")
    local ext="${filename##*.}"
    
    # Determine commit type and message based on file
    if git ls-files --error-unmatch "$file" > /dev/null 2>&1; then
        # File exists in git (modified)
        if [[ $file == *"README"* ]] || [[ $file == *"GUIDE"* ]] || [[ $file == *".md" ]]; then
            echo "docs: Update $filename"
        elif [[ $file == *"test"* ]] || [[ $file == *"Test"* ]] || [[ $file == *"spec"* ]]; then
            echo "test: Update $filename"
        elif [[ $file == *"Controller"* ]] || [[ $file == *"Service"* ]] || [[ $file == *"Repository"* ]]; then
            echo "refactor: Update $filename"
        elif [[ $ext == "java" ]] || [[ $ext == "ts" ]] || [[ $ext == "js" ]] || [[ $ext == "py" ]]; then
            echo "update: Modify $filename"
        elif [[ $ext == "css" ]] || [[ $ext == "scss" ]] || [[ $ext == "html" ]]; then
            echo "style: Update $filename"
        elif [[ $file == *"Dockerfile"* ]] || [[ $file == *"docker-compose"* ]]; then
            echo "build: Update $filename"
        elif [[ $file == *"pom.xml"* ]] || [[ $file == *"package.json"* ]]; then
            echo "build: Update dependencies in $filename"
        else
            echo "chore: Update $filename"
        fi
    else
        # New file
        if [[ $file == *"README"* ]] || [[ $file == *"GUIDE"* ]] || [[ $file == *".md" ]]; then
            echo "docs: Add $filename"
        elif [[ $file == *"test"* ]] || [[ $file == *"Test"* ]] || [[ $file == *"spec"* ]]; then
            echo "test: Add $filename"
        elif [[ $file == *"Controller"* ]] || [[ $file == *"Service"* ]] || [[ $file == *"Repository"* ]]; then
            echo "feat: Add $filename"
        elif [[ $ext == "java" ]] || [[ $ext == "ts" ]] || [[ $ext == "js" ]] || [[ $ext == "py" ]]; then
            echo "feat: Add $filename"
        elif [[ $ext == "css" ]] || [[ $ext == "scss" ]] || [[ $ext == "html" ]]; then
            echo "style: Add $filename"
        elif [[ $file == *"Dockerfile"* ]] || [[ $file == *"docker-compose"* ]]; then
            echo "build: Add $filename"
        elif [[ $file == *".sh"* ]]; then
            echo "chore: Add $filename script"
        else
            echo "feat: Add $filename"
        fi
    fi
}

# Commit each file separately
COMMIT_COUNT=0
echo "$ALL_FILES" | while IFS= read -r file; do
    if [ ! -z "$file" ]; then
        COMMIT_COUNT=$((COMMIT_COUNT + 1))
        
        # Generate commit message
        COMMIT_MSG=$(generate_commit_message "$file")
        
        # Stage the file
        git add "$file"
        
        # Commit
        git commit -m "$COMMIT_MSG" > /dev/null 2>&1
        
        if [ $? -eq 0 ]; then
            echo -e "${GREEN}✅ [$COMMIT_COUNT/$TOTAL_FILES] $COMMIT_MSG${NC}"
        else
            echo -e "${RED}❌ Failed: $file${NC}"
        fi
    fi
done

echo ""
echo -e "${GREEN}✅ All files committed separately!${NC}"
echo ""

# Ask to push
echo -e "${BLUE}🚀 Push all commits to remote?${NC}"
echo "Press Enter to push, or Ctrl+C to cancel..."
read

BRANCH=$(git rev-parse --abbrev-ref HEAD)
echo -e "${BLUE}📤 Pushing to origin/$BRANCH...${NC}"

git push origin $BRANCH

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Push successful!${NC}"
    echo -e "${GREEN}🎉 All done! $TOTAL_FILES commits pushed!${NC}"
else
    echo -e "${RED}❌ Push failed${NC}"
    echo "Run: git push -u origin $BRANCH"
fi
