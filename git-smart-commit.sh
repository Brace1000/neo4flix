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
    local lowercase_file=$(echo "$file" | tr '[:upper:]' '[:lower:]')
    
    # Check if file is modified or new
    local is_modified=false
    if git ls-files --error-unmatch "$file" > /dev/null 2>&1; then
        is_modified=true
    fi
    
    # Determine commit type based on file patterns
    local commit_type=""
    local action=""
    
    if $is_modified; then
        action="Update"
    else
        action="Add"
    fi
    
    # Documentation files
    if [[ $ext == "md" ]] || [[ $ext == "txt" ]] || [[ $ext == "rst" ]] || [[ $file == *"README"* ]] || [[ $file == *"CHANGELOG"* ]] || [[ $file == *"LICENSE"* ]]; then
        commit_type="docs"
    
    # Test files
    elif [[ $lowercase_file == *"test"* ]] || [[ $lowercase_file == *"spec"* ]] || [[ $file == *"Test."* ]] || [[ $file == *".test."* ]] || [[ $file == *".spec."* ]]; then
        commit_type="test"
    
    # Configuration files
    elif [[ $ext == "json" ]] || [[ $ext == "yaml" ]] || [[ $ext == "yml" ]] || [[ $ext == "toml" ]] || [[ $ext == "ini" ]] || [[ $ext == "conf" ]] || [[ $ext == "config" ]] || [[ $file == *".env"* ]] || [[ $filename == ".gitignore" ]] || [[ $filename == ".dockerignore" ]]; then
        commit_type="chore"
    
    # Build/CI files
    elif [[ $file == *"Dockerfile"* ]] || [[ $file == *"docker-compose"* ]] || [[ $file == *"Makefile"* ]] || [[ $file == *"pom.xml"* ]] || [[ $file == *"package.json"* ]] || [[ $file == *"package-lock.json"* ]] || [[ $file == *"yarn.lock"* ]] || [[ $file == *"Gemfile"* ]] || [[ $file == *"requirements.txt"* ]] || [[ $file == *"Cargo.toml"* ]] || [[ $file == *"go.mod"* ]] || [[ $file == *".gradle"* ]] || [[ $file == *"build.gradle"* ]]; then
        commit_type="build"
    
    # CI/CD files
    elif [[ $file == *".github/workflows"* ]] || [[ $file == *".gitlab-ci"* ]] || [[ $file == *"Jenkinsfile"* ]] || [[ $file == *".travis.yml"* ]] || [[ $file == *"circle.yml"* ]]; then
        commit_type="ci"
    
    # Style files
    elif [[ $ext == "css" ]] || [[ $ext == "scss" ]] || [[ $ext == "sass" ]] || [[ $ext == "less" ]] || [[ $ext == "html" ]] || [[ $ext == "htm" ]]; then
        commit_type="style"
    
    # Code files - check for refactor patterns
    elif $is_modified && ([[ $file == *"Controller"* ]] || [[ $file == *"Service"* ]] || [[ $file == *"Repository"* ]] || [[ $file == *"Model"* ]] || [[ $file == *"Component"* ]]); then
        commit_type="refactor"
        action="Refactor"
    
    # New feature files (code)
    elif [[ $ext == "java" ]] || [[ $ext == "ts" ]] || [[ $ext == "tsx" ]] || [[ $ext == "js" ]] || [[ $ext == "jsx" ]] || [[ $ext == "py" ]] || [[ $ext == "rb" ]] || [[ $ext == "go" ]] || [[ $ext == "rs" ]] || [[ $ext == "c" ]] || [[ $ext == "cpp" ]] || [[ $ext == "cs" ]] || [[ $ext == "php" ]] || [[ $ext == "swift" ]] || [[ $ext == "kt" ]] || [[ $ext == "scala" ]]; then
        if $is_modified; then
            commit_type="fix"
            action="Fix"
        else
            commit_type="feat"
        fi
    
    # Shell scripts
    elif [[ $ext == "sh" ]] || [[ $ext == "bash" ]] || [[ $ext == "zsh" ]]; then
        commit_type="chore"
        action="$action script"
    
    # Default
    else
        if $is_modified; then
            commit_type="chore"
        else
            commit_type="feat"
        fi
    fi
    
    echo "$commit_type: $action $filename"
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
