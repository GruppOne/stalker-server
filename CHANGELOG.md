# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

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
