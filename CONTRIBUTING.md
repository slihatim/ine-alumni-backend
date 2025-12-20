# How to contribute to the repo?
Ensure before all that an `issue` is assigned to you. (issue = a task). If not, create one for yourself.

1. Clone the repo.
2. Make sure your are on branch `main`, then add a new branch for your feature and checkout to it (See Branch naming convention below):
    ```bash
    git checkout -b <issue-number>-branch-name
    ```
3. Commit your changes.
4. Push your branch to the remote repo:
    ```bash
    git push origin HEAD
    ```
5. Head to GitHub, you will see a new branch was added, with the possibility to add a Pull Request. Click on add pull request.
6. Ensure your PR: has `no merge conflicts`, has `issue number`, and `includes a brief description`.
7. After review and approval of your PR, the changes will be merged to `main` branch.

## Branch naming convention
```
<issue-number>-branch-name
```

**Example:**
   ```
   12-implement-authorization-logic
   ```

## Development Note
- Don't implement `authentication`/`authorization` in your features. Develop assuming users are authenticated - auth will be added later via **_annotations_**.
