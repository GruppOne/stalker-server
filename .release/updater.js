// source https://semver.org/#is-there-a-suggested-regular-expression-regex-to-check-a-semver-string
const recommendedSemVerRegex = /((0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?)/m;

const before = /^version = '/m;
const after = /'$/m;
const versionNameRegex = new RegExp(before.source + recommendedSemVerRegex.source + after.source, "m");
// const versionNameRegex = /((0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?)/m;

// readVersion(contents: string): string
module.exports.readVersion = function (contents) {
  const match = versionNameRegex.exec(contents);

  if (!match) {
    throw "full version number not found while reading version";
  }

  const fullVersion = match[1];

  return fullVersion;
};

// writeVersion(contents: string, version: string): string
module.exports.writeVersion = function (contents, version) {
  const found = contents.match(versionNameRegex);
  if (!found) {
    throw "MATCH NOT FOUND";
  }

  return contents.replace(versionNameRegex, `version = '${version}'`);
};
