create database stalker-tsdb
use stalker-tsdb

precision rfc3339

# FIXME sample data should NOT be created here
INSERT access_log anonymous_key="ny7",organization_id="iii",place_id="www",inside=true
INSERT access_log anonymous_key="ny7",organization_id="iii",place_id="www",inside=false
INSERT access_log user_id="aaa",organization_id="uuu",place_id="zzz",inside=true
INSERT access_log user_id="aaa",organization_id="uuu",place_id="zzz",inside=false
