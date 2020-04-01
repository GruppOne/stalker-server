// source https://semver.org/#is-there-a-suggested-regular-expression-regex-to-check-a-semver-string
const recommendedSemVerRegex =
  "((0|[1-9]d*).(0|[1-9]d*).(0|[1-9]d*)(?:-((?:0|[1-9]d*|d*[a-zA-Z-][0-9a-zA-Z-]*)(?:.(?:0|[1-9]d*|d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:+([0-9a-zA-Z-]+(?:.[0-9a-zA-Z-]+)*))?)";

const before = "<version>";
const after = "</version>";
const versionNameRegex = new RegExp(before + recommendedSemVerRegex + after, "m");

// readVersion(contents: string): string
module.exports.readVersion = function (contents) {
  const match = versionNameRegex.exec(contents);
  const version = match[1];

  return version;
};

// writeVersion(contents: string, version: string): string
module.exports.writeVersion = function (contents, version) {
  return contents.replace(versionNameRegex, `${before}${version}${after}`);
};
