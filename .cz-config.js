const emptyScope = {
  name: "               An empty scope",
  value: "",
};

const depsScope = {
  name: "(deps)         A generic dependency",
  value: "deps",
};

const docsScope = {
  name: '(docs)         Something related to documentation',
  value: 'docs'
};

module.exports = {
  types: [
    {
      value: "feat",
      name: "feat:       A new feature",
    },
    {
      value: "fix",
      name: "fix:        A bug fix",
    },
    {
      value: "docs",
      name: "docs:       Documentation only changes",
    },
    {
      value: "style",
      name: "style:      Changes that do not affect the meaning of the code(white-space, formatting, etc)",
    },
    {
      value: "refactor",
      name: "refactor:   A code change that neither fixes a bug nor adds a feature",
    },
    {
      value: "perf",
      name: "perf:       A code change that improves performance",
    },
    {
      value: "test",
      name: "test:       Adding missing tests or correcting existing tests",
    },
    {
      value: "build",
      name: "build:      Changes that affect the build system or external dependencies",
    },
    {
      value: "ci",
      name: "ci:         Changes to our CI configuration files and scripts",
    },
    {
      value: "chore",
      name: "chore:      Other changes that don't modify src or test files",
    },
    {
      value: "revert",
      name: "revert:     Revert a previous commit",
    },
  ],

  scopes: [
    // this comments forces prettier to keep the array on multiple lines
    emptyScope,
    // TODO add relevant scopes here:
  ],

  // it needs to match the value for field type. Eg.: 'fix'
  scopeOverrides: {
    build: [
      // this comments forces prettier to keep the array on multiple lines
      emptyScope,
      depsScope,
      "docker",
      "gradle",
      "idea",
    ],
    ci: [
      // this comments forces prettier to keep the array on multiple lines
      "actions",
      emptyScope,
    ],
    chore: [
      // this comments forces prettier to keep the array on multiple lines
      emptyScope,
      "idea",
      "vscode",
      docsScope,
    ],
  },

  allowBreakingChanges: ["feat", "fix"],
  allowCustomScopes: false,
  askForBreakingChangeFirst: true,
  skipQuestions: [],
  subjectLimit: 72,
};
