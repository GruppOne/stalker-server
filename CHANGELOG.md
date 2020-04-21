# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

## 0.5.0 (2020-04-21)


### Features

* add 'organizationid' foreign key to 'place' ([d09a131](https://github.com/GruppOne/stalker-server/commit/d09a131846c03d4d04772e04036acd1d663346b4))
* add DockerFile and docker-compose for server ([64c8366](https://github.com/GruppOne/stalker-server/commit/64c8366a696ad1bbde1161a1db770fe0d215e729))
* add influxdb script and influxdb configuration ([7d08968](https://github.com/GruppOne/stalker-server/commit/7d08968af4fd1113eb7288c8475aa70a46234fea))
* add login endpoint ([5403400](https://github.com/GruppOne/stalker-server/commit/540340048bf3a6d0dd720d0287bad94e97b96cdb))
* add monitor endpoints and several tracking endpoinst ([c9aa6ec](https://github.com/GruppOne/stalker-server/commit/c9aa6ec4478f4b98db108504115be13489e63434))
* add project generated with spring initializr ([e4228f8](https://github.com/GruppOne/stalker-server/commit/e4228f89fa6ae366ebac7d9f678c058fbf586aef))
* add several endpoints \organization ([8c440eb](https://github.com/GruppOne/stalker-server/commit/8c440eba8ba20ec46bdcb4f68634c80f9872c3cf))
* add sql code to generate the database ([9605fb8](https://github.com/GruppOne/stalker-server/commit/9605fb8ef304c31ee2d9c58c07665f07b0ccca34))
* add the missing stalker mobile/web app tags ([0aa3a5f](https://github.com/GruppOne/stalker-server/commit/0aa3a5fca942fbd79b8feff563cd9e500af5f600))
* add the new structure for endpoints' entities ([804a9b8](https://github.com/GruppOne/stalker-server/commit/804a9b8fa0336f8ffcf82f3d1621629c294079af))
* add tracking endpoints ([7562bfc](https://github.com/GruppOne/stalker-server/commit/7562bfc3294557abe3beb61a9c2cfc747817f189))
* add user login endpoint ([20b2115](https://github.com/GruppOne/stalker-server/commit/20b2115e3f44909b00e11dee11ccd8b03d97f23d))
* adding two organization endpoints and one user endpoint ([1dbdc58](https://github.com/GruppOne/stalker-server/commit/1dbdc5822c2efc9003de476358ab99a8417ef09c))
* align api declaration with new schemas ([551324b](https://github.com/GruppOne/stalker-server/commit/551324b22e9d23cced024d35cb26fa1c6ee31441))
* change reference path ([ef817b0](https://github.com/GruppOne/stalker-server/commit/ef817b06dc72fd88db54bf78ee1b6154cf01a329))
* configure relational db with async driver ([3c698b3](https://github.com/GruppOne/stalker-server/commit/3c698b395b50fe3c2965c4947583c40452b8bdfa))
* continue designing user endpoints ([4c6d564](https://github.com/GruppOne/stalker-server/commit/4c6d564068fc625afa700f29c2c514593e4cdfee))
* design PoC API subset ([#1](https://github.com/GruppOne/stalker-server/issues/1)) ([c79cdaa](https://github.com/GruppOne/stalker-server/commit/c79cdaa0fe46f31f2db3f1fa3961529bfb6f3ed3))
* group dependencies into /resources subfolder ([20062b6](https://github.com/GruppOne/stalker-server/commit/20062b617be7b53c03cf3f8bcdfbc80668699fe0))
* implement 2 organization endpoints ([e8afc57](https://github.com/GruppOne/stalker-server/commit/e8afc57eb8db0c0500b524e320141680c67b5b23))
* implement correct location update endpoint ([9f637b7](https://github.com/GruppOne/stalker-server/commit/9f637b77fe0e2f83f166abe8f383314cfeafddc0))
* implement entities model ([85cd343](https://github.com/GruppOne/stalker-server/commit/85cd3439e45f20729889bae51a649ba743198fca))
* implement version endpoint ([3a63e02](https://github.com/GruppOne/stalker-server/commit/3a63e02ab433da6ac42781dbba81ad9b3f32b9b4))
* implement version endpoint ([65baedc](https://github.com/GruppOne/stalker-server/commit/65baedc3839109a4469ecf5e1828b0f8daa3ae6c))
* improvements to API ([b93e359](https://github.com/GruppOne/stalker-server/commit/b93e3595a65f652f2ef9b8b71918be5d7c0a00be))
* initial configuration ([#2](https://github.com/GruppOne/stalker-server/issues/2)) ([cd3a7a7](https://github.com/GruppOne/stalker-server/commit/cd3a7a7a3f108a394439dd857ce3ab75b3f155ce))
* modify openapi.yaml, ready to use for POC ([81fdc12](https://github.com/GruppOne/stalker-server/commit/81fdc125e6f898a3bd76e878c3cba7130ecc4f0c))
* restructure api schemas ([4add301](https://github.com/GruppOne/stalker-server/commit/4add301b2394ac714f355fedb4ab41ca9c6a3423))
* simplify configuration classes ([cbd9bfe](https://github.com/GruppOne/stalker-server/commit/cbd9bfe23f7b662c4a04089610ea9784b0a98933))
* start API design ([2a3824d](https://github.com/GruppOne/stalker-server/commit/2a3824d7460476460ca2deda22ff06a39f27fd8d))
* start cleaning up the poc endpoints ([c965205](https://github.com/GruppOne/stalker-server/commit/c965205bfb883624e8a81481579dd99df38e1956))
* update and add new endpoint for organizations ([4b904f4](https://github.com/GruppOne/stalker-server/commit/4b904f4acf16bb104706656ae61cb3816c7cd3a8))


### Bug Fixes

* add a verb at the start of the operationIds ([77b3969](https://github.com/GruppOne/stalker-server/commit/77b396901e25ddba2b631ac5ba867a35f348a307))
* add examples ([7df596d](https://github.com/GruppOne/stalker-server/commit/7df596d512010fad7ddd2b1f0c9e2310beb33649))
* always respond with valid json ([86d80cc](https://github.com/GruppOne/stalker-server/commit/86d80cc6e1c66eac23ed6a555ff15b473c36cabe))
* do some other fixes and add version's endpoint ([75b135d](https://github.com/GruppOne/stalker-server/commit/75b135d453ca549a6f1dd7d6408bd003c88db7ba))
* fix descriptions and operationsIds ([68a6fe9](https://github.com/GruppOne/stalker-server/commit/68a6fe920615b3bf5d6521b4d30501c9d9104e4f))
* fix environment part in docker-compose ([319a577](https://github.com/GruppOne/stalker-server/commit/319a577d03b2ae7da458b7168e133fa214a0a8af))
* fix linter errors ([c541026](https://github.com/GruppOne/stalker-server/commit/c541026e8e719aee8e44e3b782d8e9ddae51e195))
* fix name file .env.example in .env ([3dfcab5](https://github.com/GruppOne/stalker-server/commit/3dfcab53f691028571387a1e896600ed7c281920))
* fix some irregularities in api documents ([89a083e](https://github.com/GruppOne/stalker-server/commit/89a083edbcaca907d03d959a077a01c0123b06ac))
* fix some other typos ([5835fbd](https://github.com/GruppOne/stalker-server/commit/5835fbdce6b7e83309e82dfafc336bf9b4b4b8b3))
* fix some typos and add  bearer JWT tokens ([ba13c63](https://github.com/GruppOne/stalker-server/commit/ba13c639794e5a736efd2edffea75552676a8990))
* fix type ([25ae7cf](https://github.com/GruppOne/stalker-server/commit/25ae7cfcab361a33c73144a62228039ac462c17c))
* make org data required ([58e4b85](https://github.com/GruppOne/stalker-server/commit/58e4b8578db7bbfe6702a42737cea9eda2c4be5c))
* remove leftover files and rename endpoint ([efde945](https://github.com/GruppOne/stalker-server/commit/efde945057c482954b17f019c4c6dcf7d831714e))
* remove misused discriminator property ([e8fd0c5](https://github.com/GruppOne/stalker-server/commit/e8fd0c530a39dea84406d849e6b92fa2ed8ba2ea))
* remove validation patterns ([4053b9b](https://github.com/GruppOne/stalker-server/commit/4053b9b412e86ca4f3f5047f35965fc65033fa00))
* remove wrongly committed folder and simplify gitignores structure ([3c9d682](https://github.com/GruppOne/stalker-server/commit/3c9d6820cdc685dc946b9ff1517d6bb676f47840))
* replace id with userId in the paths ([7c68fc5](https://github.com/GruppOne/stalker-server/commit/7c68fc5d79284d7627f720ba956dd80ca510b764))
* resolve some fixme and add new ones ([b3606ce](https://github.com/GruppOne/stalker-server/commit/b3606ce8c01acda92d6078d1848122de63c1acba))
* solve refs problems ([5c70574](https://github.com/GruppOne/stalker-server/commit/5c70574c77cae7574b5ce1d8cccd14b35e52ef96))
* solve warnings and syntax errors, replace id with userId ([eebb837](https://github.com/GruppOne/stalker-server/commit/eebb8370e4dcede00f0f18b6ad00cb5a3f8715b9))
* switch summary and description of endpoint ([22d03e5](https://github.com/GruppOne/stalker-server/commit/22d03e56f22e3d8b2d7d3ca54c6339b4dee6743f))


### Documentation

* add fields set schema for timeseries db ([dd10cd6](https://github.com/GruppOne/stalker-server/commit/dd10cd6eba3be6bc0f155bd2d08f0ee5cde90722))
* add usage documentation ([023de4f](https://github.com/GruppOne/stalker-server/commit/023de4f163408c171d3a1ca37507215b29203555))
* better specify instructions for windows ([50bfdd6](https://github.com/GruppOne/stalker-server/commit/50bfdd6a3d045d205df38b7abefb47cde9e39096))
* change influxdb schema and add comments ([22e18ad](https://github.com/GruppOne/stalker-server/commit/22e18adb836a80ec1893b1081fce9daa1e007b80))
* improve documented procedure ([a022a3f](https://github.com/GruppOne/stalker-server/commit/a022a3ffbd9dcb766347d42724bbf56f607f8714))
* start writing the api style guide ([9d1e15f](https://github.com/GruppOne/stalker-server/commit/9d1e15fcc7e762d090297164a3fcd1db2b3e407f))


### Configuration

* add database configuration ([4f6c4a5](https://github.com/GruppOne/stalker-server/commit/4f6c4a506306f892abcfbc9264c2d8bfc190d30e))
* add dump data file to test db ([740e62a](https://github.com/GruppOne/stalker-server/commit/740e62a2da9f36a551f24372dafc70375bfeb274))
* cleanup unnecessary stuff ([851d10b](https://github.com/GruppOne/stalker-server/commit/851d10b938cc5b6a15847696fcc8efc37b8d1102))
* switch 'he' for gender neutral 'they' ([6b574d7](https://github.com/GruppOne/stalker-server/commit/6b574d7135473e23fcfddf143b037831d0f961cf))
* **docker:** configure database services ([bc6c5d0](https://github.com/GruppOne/stalker-server/commit/bc6c5d0f0219faab88582a3d617583a890a3cb7c))
* **docker:** move services from override to base ([21fe49e](https://github.com/GruppOne/stalker-server/commit/21fe49ee008455d48c99305ebafc5ce032b68033))
* **docker:** rdb service is now working as expected ([ba431a1](https://github.com/GruppOne/stalker-server/commit/ba431a1a39f6aae77e84e57fa662407213ec9e3c))
* **docker:** switch back to mysql and fix docker errors ([472051b](https://github.com/GruppOne/stalker-server/commit/472051b70a48905d458f62bee6bbb14d65203612))
* **docs:** add documentation specific gitignore ([b580c4c](https://github.com/GruppOne/stalker-server/commit/b580c4cd294d2563b30603581fc9bc0072e7e975))
* **docs:** add style for uml class diagrams ([e5c8f89](https://github.com/GruppOne/stalker-server/commit/e5c8f89be40acb8081125dc450d04fb2ab6893dc))
* **gradle:** remove non-essential dependencies ([27505fc](https://github.com/GruppOne/stalker-server/commit/27505fcb406c6e9a602ae3b16d5f4d24f08f792e))
* **gradle:** switch to spring boot 2.3.0 M4 ([9657171](https://github.com/GruppOne/stalker-server/commit/96571718af992479399caf9f2a3790220dc5ab80))
* **release:** 0.1.0 ([9fdc6c1](https://github.com/GruppOne/stalker-server/commit/9fdc6c1551e51364a47c0690656dcac4a70c2277))
* **release:** 0.2.0 ([ad0ce79](https://github.com/GruppOne/stalker-server/commit/ad0ce79aa01e6fe263902cea0a883af0b680fda6))
* **release:** 0.3.0 ([3cffe0c](https://github.com/GruppOne/stalker-server/commit/3cffe0cfe826ccfc410807bc3e20a3b94ee2312c))
*  commit unfinished work ([907c913](https://github.com/GruppOne/stalker-server/commit/907c913fde3208786ce3a3a4d11bb222caac4952))
* add default NULL to Organizations.ldapConfiguration ([58fec82](https://github.com/GruppOne/stalker-server/commit/58fec82ab2bfecf44133f09bebb98438576480ea))
* add docs scope to chore type for git cz ([a0d266b](https://github.com/GruppOne/stalker-server/commit/a0d266bf88d9ea5ec0f1285211752aa5c5a8d45f))
* add folder structure for database related files ([75fee8b](https://github.com/GruppOne/stalker-server/commit/75fee8b0bcc25ec2705ea4eacd3e47e8bf172bb4))
* add linter for openapi documents ([54399e3](https://github.com/GruppOne/stalker-server/commit/54399e387c8b2ab25fcb80e92c82714e8029cb6a))
* add release scripts and markdown lint workflow ([6189a1a](https://github.com/GruppOne/stalker-server/commit/6189a1ac2dce14cfd6f9943115e39acf299e270b))
* add several improvements to sql file ([ca8a60d](https://github.com/GruppOne/stalker-server/commit/ca8a60d95a3116362f1377f42f0b19e5e02d15c1))
* add some missing settings ([4b28cd6](https://github.com/GruppOne/stalker-server/commit/4b28cd6bb14adb30b7d8e572ac9d7347bd3e410e))
* change data types of date columns to datetime ([73e54a9](https://github.com/GruppOne/stalker-server/commit/73e54a9eec60594a8d44a1609d32de5a83e2ca2d))
* fix errors from last review ([f106816](https://github.com/GruppOne/stalker-server/commit/f1068168f1290fe2b199ac58344286bc1907794d))
* fix releaser script ([c1b4f5d](https://github.com/GruppOne/stalker-server/commit/c1b4f5d5f786d5b63dde9cad61cc4685e5652895))
* **docs:** add task for image generatin with plantuml ([0827750](https://github.com/GruppOne/stalker-server/commit/08277508108038be474033e9a97b01b66ea97dce))
* checkout configuration from other branch ([5c8fded](https://github.com/GruppOne/stalker-server/commit/5c8fded5cccc8b28f1d17125ca0b5ee3c128483c))
* final touches to configuration ([06a8a94](https://github.com/GruppOne/stalker-server/commit/06a8a94b175f39dd4282530d8c101ce3686acd5b))
* fix releaser script ([d5af280](https://github.com/GruppOne/stalker-server/commit/d5af28058869c2274cfd73da15c1edc7934d02fd))
* improve releaser scripts ([c9b7c0b](https://github.com/GruppOne/stalker-server/commit/c9b7c0b811cff4720aa1d6f77cb3876aec25b163))
* init ([87512cb](https://github.com/GruppOne/stalker-server/commit/87512cb3e54005d316a4f667040dd24d512a3d2b))
* prepare gitignore for gradle-based spring project ([f49c0a6](https://github.com/GruppOne/stalker-server/commit/f49c0a6c2209de82ce74ea4ca7281116a417d502))
* remove excess dependencies and configure releaser ([90d3d4b](https://github.com/GruppOne/stalker-server/commit/90d3d4b383f7375d5d863efbf1876c2d6e9051fb))
* rename a bunch of files and commit .idea config ([d6fc188](https://github.com/GruppOne/stalker-server/commit/d6fc1884b10b4a7828fdc201dd88ab06446398af))
* some changes to configuration ([8aebad8](https://github.com/GruppOne/stalker-server/commit/8aebad8541cb7ebdc3ccbe7f48ab78099eac9171))
* streamline some settings in containers and add .env to gitignore ([b7a9ee1](https://github.com/GruppOne/stalker-server/commit/b7a9ee16aa907f63ee8adbe7ace6dec358a3ff0d))
* update configuration ([00aaee9](https://github.com/GruppOne/stalker-server/commit/00aaee95686762cd9b9a58a333fd8837fdca8217))
* update extension list and change commitizen adapter ([021c51c](https://github.com/GruppOne/stalker-server/commit/021c51c1d3c76b1d3f90c928df9c834a2d054f17))
* update password and email pattern with the ones used in webapp ([bf41542](https://github.com/GruppOne/stalker-server/commit/bf415425ee994a90ba7e422eb774f6adf6ff99c9))

## [0.3.0](https://github.com/GruppOne/stalker-server/compare/v0.1.0...v0.3.0) (2020-04-07)


### Features

* add login endpoint ([20bacfc](https://github.com/GruppOne/stalker-server/commit/20bacfcd04f65cffe1178b3a2044df545edace9d))
* add the new structure for endpoints' entities ([225e719](https://github.com/GruppOne/stalker-server/commit/225e7193777c2cd44014f10905450637b24ed8d3))
* add user login endpoint ([5428780](https://github.com/GruppOne/stalker-server/commit/542878031c9a6e704ed06d35e07e0c077eb24e46))
* design PoC API subset ([#1](https://github.com/GruppOne/stalker-server/issues/1)) ([4f880ca](https://github.com/GruppOne/stalker-server/commit/4f880ca1d45164719a22695df1269e81cc387afb))
* implement 2 organization endpoints ([01e6db6](https://github.com/GruppOne/stalker-server/commit/01e6db681e284fbd93a2f566d84ded5872302ec6))
* implement entities model ([eedd487](https://github.com/GruppOne/stalker-server/commit/eedd487c7617704625d7dad3d181652fb594532a))
* implement version endpoint ([e670e14](https://github.com/GruppOne/stalker-server/commit/e670e141ff4e2f1b7f6ad38cc27be5f3994b1536))
* simplify configuration classes ([4b9ccf7](https://github.com/GruppOne/stalker-server/commit/4b9ccf789137614bb97f8dfe142945371aaf3713))


### Configuration

*  commit unfinished work ([97ed1c6](https://github.com/GruppOne/stalker-server/commit/97ed1c6a966781cf6391cfd2db9d9d8dc5afda78))

## [0.2.0](https://github.com/GruppOne/stalker-server/compare/v0.1.0...v0.2.0) (2020-04-06)


### Features

* add 'organizationid' foreign key to 'place' ([e90743a](https://github.com/GruppOne/stalker-server/commit/e90743a34656636cc6b5bb0e0549cc073690e2bb))
* add monitor endpoints and several tracking endpoinst ([24840f5](https://github.com/GruppOne/stalker-server/commit/24840f5421904ae208618c446db6659d9e666ebd))
* add several endpoints \organization ([4f576bf](https://github.com/GruppOne/stalker-server/commit/4f576bf6c1228407ab5472f46e18d393f9ce3cf8))
* add sql code to generate the database ([92b680f](https://github.com/GruppOne/stalker-server/commit/92b680f20b62d5608dd309247de03134867cec6b))
* add the missing stalker mobile/web app tags ([19b8e8f](https://github.com/GruppOne/stalker-server/commit/19b8e8f116de5775f9422b3f83e5247e6e3f4e02))
* add tracking endpoints ([77f8f3a](https://github.com/GruppOne/stalker-server/commit/77f8f3a93865b50e8aeadc186817b1944e953d85))
* adding two organization endpoints and one user endpoint ([f6e2af7](https://github.com/GruppOne/stalker-server/commit/f6e2af7c29990f59c4bf087d4adbd75005b0a9d2))
* change reference path ([c5c4754](https://github.com/GruppOne/stalker-server/commit/c5c47545a298dbe2691fa8c37afc195d3f88f4cb))
* continue designing user endpoints ([238c340](https://github.com/GruppOne/stalker-server/commit/238c340caf6cc29df9dc6f6386498ea718930761))
* group dependencies into /resources subfolder ([185f40c](https://github.com/GruppOne/stalker-server/commit/185f40c9a22fa5b683499ec8bcbcaef64552f6cc))
* implement correct location update endpoint ([4cb317f](https://github.com/GruppOne/stalker-server/commit/4cb317f08522c8917713b05b858e41f125394baa))
* improvements to API ([af2a876](https://github.com/GruppOne/stalker-server/commit/af2a876bc06909f4029012d3893c9e8c0c36514c))
* modify openapi.yaml, ready to use for POC ([0d98dfa](https://github.com/GruppOne/stalker-server/commit/0d98dfae027d509194d36634a9f9a9e379774d0d))
* start API design ([b24c48e](https://github.com/GruppOne/stalker-server/commit/b24c48e02e22ab91ba2a2434d00f7667b5fd4e23))
* start cleaning up the poc endpoints ([2a853d6](https://github.com/GruppOne/stalker-server/commit/2a853d621d10301e439e439dfd6e518d3b201633))
* update and add new endpoint for organizations ([8b74b57](https://github.com/GruppOne/stalker-server/commit/8b74b57566e401dd7f1e316bcfcd68ccf8bdb9af))


### Bug Fixes

* add a verb at the start of the operationIds ([7839854](https://github.com/GruppOne/stalker-server/commit/7839854bf3ae0d1d954cf6368b46ce8b21984fd8))
* always respond with valid json ([13952eb](https://github.com/GruppOne/stalker-server/commit/13952ebd8617dd4b3e7cb3d5ea90be8feb02e291))
* fix descriptions and operationsIds ([a292bdd](https://github.com/GruppOne/stalker-server/commit/a292bdd31cce4dcb25055f70a910240d267555d1))
* fix some irregularities in api documents ([5e889be](https://github.com/GruppOne/stalker-server/commit/5e889be6840038301ec0da2f31784b914d92275b))
* remove misused discriminator property ([805a03c](https://github.com/GruppOne/stalker-server/commit/805a03ca75da30421f4d2010bcfac5295bb426d2))
* replace id with userId in the paths ([5fed6bb](https://github.com/GruppOne/stalker-server/commit/5fed6bb3c1530f9a0095d5085d741ae19731a29e))
* resolve some fixme and add new ones ([e7e3151](https://github.com/GruppOne/stalker-server/commit/e7e31516552d7798dda293925c2030904ef693fe))
* solve refs problems ([d0ade6e](https://github.com/GruppOne/stalker-server/commit/d0ade6e969b8c1c985a7be9e4a730d7177bfad5c))
* solve warnings and syntax errors, replace id with userId ([d157951](https://github.com/GruppOne/stalker-server/commit/d1579518e89b80b50a9f85b7be8f7879fba8f84a))


### Documentation

* start writing the api style guide ([3c80103](https://github.com/GruppOne/stalker-server/commit/3c80103675469d00024171b73b6e4d9a34d06d21))


### Configuration

* add docs scope to chore type for git cz ([148e306](https://github.com/GruppOne/stalker-server/commit/148e306d69cc0f70504d44ceacbe3f3eb3c51e1e))
* add linter for openapi documents ([c8730da](https://github.com/GruppOne/stalker-server/commit/c8730dae2512d1250e91329c570de2b49910241c))
* fix errors from last review ([32dfd9a](https://github.com/GruppOne/stalker-server/commit/32dfd9aca6bd15f6333127e28a3468dd7d8f523f))
* fix releaser script ([329f580](https://github.com/GruppOne/stalker-server/commit/329f580d5dda60e7e3f4cc348172b24f97c0560d))
* **docs:** add documentation specific gitignore ([42b72e9](https://github.com/GruppOne/stalker-server/commit/42b72e9bbd6af604c72cc5ff24e455536fe7251d))
* **docs:** add style for uml class diagrams ([ec291d5](https://github.com/GruppOne/stalker-server/commit/ec291d51793ca31f91f0dfbea4c983312931c30a))
* **docs:** add task for image generatin with plantuml ([7620bb6](https://github.com/GruppOne/stalker-server/commit/7620bb6113d079b43b761c4f101447f2f576d955))
* add .idea to .gitignore ([a4c4b1f](https://github.com/GruppOne/stalker-server/commit/a4c4b1f1608a38670505d1f5234df412e43e2472))
* add folder structure for database related files ([4f87777](https://github.com/GruppOne/stalker-server/commit/4f8777722b5aa3cfa3ca52353ccf815e217232ae))
* checkout configuration from other branch ([fae6726](https://github.com/GruppOne/stalker-server/commit/fae67269fece6d0a3704d0371e91c569ed38b584))
* update configuration ([5d935c0](https://github.com/GruppOne/stalker-server/commit/5d935c065c6a8e422f753340e99d375ed3a763bd))
* update password and email pattern with the ones used in webapp ([8f32fab](https://github.com/GruppOne/stalker-server/commit/8f32fab2e807ef0598fddf53cd0b10fc6e71a80f))
* update vscode settings ([9add93a](https://github.com/GruppOne/stalker-server/commit/9add93a8098805230ef2c332e594e2ec889059a9))

## 0.1.0 (2020-04-04)


### Features

* add DockerFile and docker-compose for server ([64c8366](https://github.com/GruppOne/stalker-server/commit/64c8366a696ad1bbde1161a1db770fe0d215e729))
* add influxdb script and influxdb configuration ([7d08968](https://github.com/GruppOne/stalker-server/commit/7d08968af4fd1113eb7288c8475aa70a46234fea))
* add project generated with spring initializr ([e4228f8](https://github.com/GruppOne/stalker-server/commit/e4228f89fa6ae366ebac7d9f678c058fbf586aef))
* implement version endpoint ([65baedc](https://github.com/GruppOne/stalker-server/commit/65baedc3839109a4469ecf5e1828b0f8daa3ae6c))


### Bug Fixes

* fix environment part in docker-compose ([319a577](https://github.com/GruppOne/stalker-server/commit/319a577d03b2ae7da458b7168e133fa214a0a8af))
* fix name file .env.example in .env ([3dfcab5](https://github.com/GruppOne/stalker-server/commit/3dfcab53f691028571387a1e896600ed7c281920))


### Documentation

* add fields set schema for timeseries db ([dd10cd6](https://github.com/GruppOne/stalker-server/commit/dd10cd6eba3be6bc0f155bd2d08f0ee5cde90722))
* change influxdb schema and add comments ([22e18ad](https://github.com/GruppOne/stalker-server/commit/22e18adb836a80ec1893b1081fce9daa1e007b80))


### Configuration

* add default NULL to Organizations.ldapConfiguration ([58fec82](https://github.com/GruppOne/stalker-server/commit/58fec82ab2bfecf44133f09bebb98438576480ea))
* **docker:** switch back to mysql and fix docker errors ([472051b](https://github.com/GruppOne/stalker-server/commit/472051b70a48905d458f62bee6bbb14d65203612))
* add several improvements to sql file ([ca8a60d](https://github.com/GruppOne/stalker-server/commit/ca8a60d95a3116362f1377f42f0b19e5e02d15c1))
* add some missing settings ([4b28cd6](https://github.com/GruppOne/stalker-server/commit/4b28cd6bb14adb30b7d8e572ac9d7347bd3e410e))
* final touches to configuration ([06a8a94](https://github.com/GruppOne/stalker-server/commit/06a8a94b175f39dd4282530d8c101ce3686acd5b))
* fix releaser script ([d5af280](https://github.com/GruppOne/stalker-server/commit/d5af28058869c2274cfd73da15c1edc7934d02fd))
* **gradle:** remove non-essential dependencies ([27505fc](https://github.com/GruppOne/stalker-server/commit/27505fcb406c6e9a602ae3b16d5f4d24f08f792e))
* add release scripts and markdown lint workflow ([6189a1a](https://github.com/GruppOne/stalker-server/commit/6189a1ac2dce14cfd6f9943115e39acf299e270b))
* change data types of date columns to datetime ([73e54a9](https://github.com/GruppOne/stalker-server/commit/73e54a9eec60594a8d44a1609d32de5a83e2ca2d))
* improve releaser scripts ([c9b7c0b](https://github.com/GruppOne/stalker-server/commit/c9b7c0b811cff4720aa1d6f77cb3876aec25b163))
* init ([87512cb](https://github.com/GruppOne/stalker-server/commit/87512cb3e54005d316a4f667040dd24d512a3d2b))
* prepare gitignore for gradle-based spring project ([f49c0a6](https://github.com/GruppOne/stalker-server/commit/f49c0a6c2209de82ce74ea4ca7281116a417d502))
* remove excess dependencies and configure releaser ([90d3d4b](https://github.com/GruppOne/stalker-server/commit/90d3d4b383f7375d5d863efbf1876c2d6e9051fb))
* rename a bunch of files and commit .idea config ([d6fc188](https://github.com/GruppOne/stalker-server/commit/d6fc1884b10b4a7828fdc201dd88ab06446398af))
* some changes to configuration ([8aebad8](https://github.com/GruppOne/stalker-server/commit/8aebad8541cb7ebdc3ccbe7f48ab78099eac9171))
* streamline some settings in containers and add .env to gitignore ([b7a9ee1](https://github.com/GruppOne/stalker-server/commit/b7a9ee16aa907f63ee8adbe7ace6dec358a3ff0d))
* update extension list and change commitizen adapter ([021c51c](https://github.com/GruppOne/stalker-server/commit/021c51c1d3c76b1d3f90c928df9c834a2d054f17))
