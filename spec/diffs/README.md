# What changed, version by version

Archived Oracle Essbase REST API specifications and the differences between
them. Regenerate with `python3 spec/generate.py`; see
[../README.md](../README.md) for where the specs come from.

[coverage.md](coverage.md) turns this around and asks the other question:
of everything the newest API offers, what does this client already reach?

| Step | Endpoints | Models | Report |
| --- | --- | --- | --- |
| 21.1 to 21.4 | 9 added, 3 removed, 10 changed | 8 added, 2 removed, 8 changed | [21.1-to-21.4.md](21.1-to-21.4.md) |
| 21.4 to 21.5 | 1 added, 0 removed, 4 changed | 2 added, 0 removed, 2 changed | [21.4-to-21.5.md](21.4-to-21.5.md) |
| 21.5 to 21.7 | 17 added, 0 removed, 7 changed | 6 added, 0 removed, 3 changed | [21.5-to-21.7.md](21.5-to-21.7.md) |
| 21.7 to 26.1 | 139 added, 6 removed, 34 changed | 20 added, 10 removed, 19 changed | [21.7-to-26.1.md](21.7-to-26.1.md) |

## Endpoints added, by version

### 21.4

- `DELETE` `/applications/{applicationName}/databases/{databaseName}/asodataload/buffers` - Destroy Dataload Buffer
- `DELETE` `/files/abort/{path}` - Abort File Upload
- `DELETE` `/jobs/purge` - Delete jobs
- `GET` `/files/uploadconfig` - getUploadConfig
- `POST` `/applications/{applicationName}/databases/{databaseName}/reports/{name}` - Execute Drill Through Report
- `POST` `/datasources/customdelimited/query/stream` - Get Streamed Datasource Results
- `POST` `/files/upload-commit/{path}` - Upload Commit
- `POST` `/files/upload-create/{path}` - Create Upload
- `PUT` `/files/upload-part/{path}` - Upload the part

### 21.5

- `GET` `/applications/{applicationName}/databases/{databaseName}/settings/compressioninfo` - Get Compression Settings

### 21.7

- `DELETE` `/centralizedurl` - Delete Essbase server from Centralized URL
- `DELETE` `/rocluster` - Delete ReadOnly Cluster
- `GET` `/applications/partitions/supportedfederatedtypes` - Get Supported Partition Types
- `GET` `/applications/{applicationName}/encryptionconfig` - Application Encryption supported methods
- `GET` `/centralizedurl` - Get Essbase Server list
- `GET` `/rocluster` - Get ReadOnly Cluster List
- `GET` `/rocluster/findByName` - Find ReadOnly Cluster By Name
- `GET` `/rocluster/{svrName}/appcubelist` - Get App Cube List
- `POST` `/applications/{applicationName}/databases/{databaseName}/dtreports/list` - List Drill Through Reports For Given Cell Intersections
- `POST` `/applications/{applicationName}/decrypt` - Application Encryption
- `POST` `/applications/{applicationName}/encrypt` - Application Encryption
- `POST` `/centralizedurl` - Add Essbase server to Centralized URL List
- `POST` `/files/actions/extractJob` - Extract Zip File Using a Job
- `POST` `/rocluster` - Create ReadOnly Cluster
- `POST` `/rocluster/changestatus` - ReadOnly Cluster Change Status
- `PUT` `/centralizedurl` - Update Essbase URL of Centralized URL List
- `PUT` `/rocluster` - Update ReadOnly Cluster

### 26.1

- `DELETE` `/ai/aiconnection/{aiConnectionName}/chat/profile/{profileName}` - Drop OCI chat profile
- `DELETE` `/ai/aiconnection/{aiConnectionName}/job/vectorindex` - Create a job to drop the vector index
- `DELETE` `/ai/applications/{applicationName}/connection` - Dissociate AI connection
- `DELETE` `/ai/applications/{applicationName}/databases/{databaseName}/calculation/conversation/{conversationId}` - Drop a conversation.
- `DELETE` `/ai/applications/{applicationName}/databases/{databaseName}/conversationHistory` - Delete conversation history
- `DELETE` `/ai/dbconnection/{dbConnectionName}/chat/credential/{credentialName}` - Drop OCI chat credential
- `DELETE` `/ai/dbconnection/{dbConnectionName}/vector/credential/{credentialName}` - Drop OCI vector credential
- `DELETE` `/federatedoverdatasource/mapping` - deleteMapping
- `DELETE` `/outline/{app}/{cube}/edit` - deleteMember
- `DELETE` `/outline/{app}/{cube}/qedit` - deleteMember_1
- `GET` `/ai/aiconnection/{aiConnectionName}/vectorindex/{vectorIndexName}/narrate/{profileName}` - AI Narrate
- `GET` `/ai/applications/{applicationName}/databases/{databaseName}/calculation/conversation` - List all calculation specific Conversations IDs
- `GET` `/ai/applications/{applicationName}/databases/{databaseName}/calculation/conversation/{conversationId}` - Get all calculation specific conversations.
- `GET` `/ai/applications/{applicationName}/databases/{databaseName}/conversation` - List conversations
- `GET` `/ai/applications/{applicationName}/databases/{databaseName}/conversationHistory` - Get conversation history
- `GET` `/ai/connection` - Get AI connection
- `GET` `/ai/vectorindex` - Get vector index
- `GET` `/amw` - read
- `GET` `/applications/{applicationName}/datasources/{datasourceName}` - Get Application Datasource
- `GET` `/backup/application/{applicationId}` - getApplicationBackups
- `GET` `/backup/applications` - getApplications
- `GET` `/backup/info` - getBackupInfo
- `GET` `/backup/isrunning` - isRunning
- `GET` `/backup/queue` - getQueueInfo
- `GET` `/backup/settings` - getSettings
- `GET` `/backup/settings/validation` - getSettingsValidator
- `GET` `/backup/system` - getSystemBackups
- `GET` `/catalog_old/{fileName}` - getFileContent
- `GET` `/cfg` - Get Essbase Configuration.
- `GET` `/cloudstorage/config` - Get object storage configuration
- `GET` `/connections/{connection}/getdependentconnections` - List dependamt connection names
- `GET` `/datasources/{datasourceName}` - Get Global Datasource
- `GET` `/ess-mcp` - doGet
- `GET` `/ess-mcp/tools` - listTools
- `GET` `/fastwriteback/checkSQLload` - checkIfSQLLoad
- `GET` `/federatedoverdatasource/av/avparams` - avParams
- `GET` `/federatedoverdatasource/av/status` - avStatus
- `GET` `/federatedoverdatasource/av/validate` - validate
- `GET` `/federatedoverdatasource/cancel` - cancelRequest
- `GET` `/federatedoverdatasource/factmanaged/settings` - factManagedSettings
- `GET` `/federatedoverdatasource/lastcommit` - getFedVersion
- `GET` `/federatedoverdatasource/mapping` - getMapping
- `GET` `/federatedoverdatasource/mapping/cr` - getMappingCR
- `GET` `/federatedoverdatasource/mapping/denormalized` - getMappingDenormalized
- `GET` `/files/getDatabasesFromLCMZip` - Get database names from LCM zip file
- `GET` `/files/getobjectstoreuri` - getObjectStoreURI
- `GET` `/logs` - Get All Server Types
- `GET` `/logs/stream` - Return Filtered Logs
- `GET` `/logs/{serverType}` - Download Logs for Server Type
- `GET` `/logs/{serverType}/all` - Download All Logs
- `GET` `/logs/{serverType}/latest` - Download Latest Log
- `GET` `/odbc` - read_1
- `GET` `/outline/{app}/{cube}/edit` - getMembers_1
- `GET` `/outline/{app}/{cube}/edit/ancestors/{memberUniqueName}` - getAncestorsMemberInfo
- `GET` `/outline/{app}/{cube}/edit/copy/status` - getCopyMembersStatus
- `GET` `/outline/{app}/{cube}/edit/descendantsCount/{memberUniqueName}` - getDescendantsCount
- `GET` `/outline/{app}/{cube}/edit/getRelatedInfo` - getRelatedInfo
- `GET` `/outline/{app}/{cube}/edit/memberSelection` - memberSelection
- `GET` `/outline/{app}/{cube}/edit/settings/aliases` - getAliasTableNames
- `GET` `/outline/{app}/{cube}/edit/settings/hierarchyDetails` - getHierarchyDetails
- `GET` `/outline/{app}/{cube}/edit/settings/outline` - getOutlineSettings
- `GET` `/outline/{app}/{cube}/edit/settings/smartlist` - getAllSmartList
- `GET` `/outline/{app}/{cube}/edit/shared` - getSharedMembers
- `GET` `/outline/{app}/{cube}/edit/validate` - validate_1
- `GET` `/outline/{app}/{cube}/edit/{memberUniqueName}` - getMemberInfo
- `GET` `/outline/{app}/{cube}/pivotDimension` - Get Pivot dimension
- `GET` `/outline/{app}/{cube}/qedit` - getMembers_3
- `GET` `/outline/{app}/{cube}/qedit/ancestors/{memberUniqueName}` - getAncestorsMemberInfo_1
- `GET` `/outline/{app}/{cube}/qedit/descendantsCount/{memberUniqueName}` - getDescendantsCount_1
- `GET` `/outline/{app}/{cube}/qedit/memberSelection` - memberSelection_1
- `GET` `/outline/{app}/{cube}/qedit/settings/aliases` - getAliasTableNames_1
- `GET` `/outline/{app}/{cube}/qedit/settings/outline` - getOutlineSettings_1
- `GET` `/outline/{app}/{cube}/qedit/settings/smartlist` - getAllSmartList_1
- `GET` `/outline/{app}/{cube}/qedit/shared` - getSharedMembers_1
- `GET` `/outline/{app}/{cube}/qedit/validate` - validate_2
- `GET` `/outline/{app}/{cube}/qedit/{memberUniqueName}` - getMemberInfo_1
- `GET` `/service/status` - getStatus
- `GET` `/version` - getVersion
- `PATCH` `/outline/{app}/{cube}/edit` - updateMember
- `PATCH` `/outline/{app}/{cube}/qedit` - updateMember_1
- `POST` `/ai/aiconnection/{aiConnectionName}/chat/profile/{profileName}` - Create OCI chat profile
- `POST` `/ai/aiconnection/{aiConnectionName}/job/vectorindex` - Create a job to build vector index
- `POST` `/ai/applications/{applicationName}/chat/passThrough` - AI pass through
- `POST` `/ai/applications/{applicationName}/connection/{connectionName}` - Associate AI connection
- `POST` `/ai/applications/{applicationName}/databases/{databaseName}/calculation/generate` - Generate calculation script.
- `POST` `/ai/applications/{applicationName}/databases/{databaseName}/listSampleQueries` - List Sample Queries
- `POST` `/ai/applications/{applicationName}/databases/{databaseName}/mdxgenerator` - MDX Generator
- `POST` `/ai/applications/{applicationName}/databases/{databaseName}/nnearestneighboursearch` - N-Nearest Neighbour Search
- `POST` `/ai/applications/{applicationName}/databases/{databaseName}/semanticsearch` - Semantic Search
- `POST` `/ai/applications/{applicationName}/databases/{databaseName}/vectorizationDate` - Vectorization Date
- `POST` `/ai/applications/{applicationName}/job/vectorize/databases/{databaseName}` - Vectorize outline job
- `POST` `/ai/dbconnection/{dbConnectionName}/chat/credential/signingkey/{credentialName}` - Create OCI chat credential using singing key
- `POST` `/ai/dbconnection/{dbConnectionName}/vector/credential/{credentialName}` - Create OCI vector credential
- `POST` `/applications/{applicationName}/databases/{databaseName}/scripts/{scriptName}/scriptops/validate` - Validate Calc Script
- `POST` `/backup/applications` - backupApplications
- `POST` `/backup/start` - startBackup
- `POST` `/backup/stop` - stopBackup
- `POST` `/cloudstorage/config/test` - Verify object storage configuration
- `POST` `/ess-mcp` - doPost
- `POST` `/ess-mcp/call` - callTool
- `POST` `/event/{eventName}` - fireEvent
- `POST` `/fastwriteback/adwload` - load
- `POST` `/federatedoverdatasource` - report
- `POST` `/federatedoverdatasource/av/createAv` - createAv
- `POST` `/federatedoverdatasource/clearregions` - clear
- `POST` `/federatedoverdatasource/saveformulas` - saveFormulas
- `POST` `/federatedoverdatasource/saveformulas/cache` - cacheFormulas
- `POST` `/federatedoverdatasource/writeback` - writeback
- `POST` `/federatedsample` - report_1
- `POST` `/outline/{app}/{cube}/edit` - outlineAction
- `POST` `/outline/{app}/{cube}/edit/copy` - copyMembers
- `POST` `/outline/{app}/{cube}/edit/currencyDatabase` - createCurrencyDB
- `POST` `/outline/{app}/{cube}/edit/direct` - outlineEdit
- `POST` `/outline/{app}/{cube}/edit/exportaliastable` - exportAliasTable
- `POST` `/outline/{app}/{cube}/edit/importaliastable` - importAliasTable
- `POST` `/outline/{app}/{cube}/edit/members` - getMembers
- `POST` `/outline/{app}/{cube}/edit/motf` - setMOTF
- `POST` `/outline/{app}/{cube}/qedit` - outlineAction_1
- `POST` `/outline/{app}/{cube}/qedit/currencyDatabase` - createCurrencyDB_1
- `POST` `/outline/{app}/{cube}/qedit/direct` - outlineEdit_1
- `POST` `/outline/{app}/{cube}/qedit/members` - getMembers_2
- `POST` `/outline/{app}/{cube}/qedit/motf` - setMOTF_1
- `POST` `/security/syncwithidp` - syncWithIDP
- `POST` `/service/actions/start` - start
- `POST` `/service/actions/stop` - stop
- `PUT` `/amw` - write
- `PUT` `/applications/{applicationName}/databases/{databaseName}/scripts/{scriptName}/content` - uploadScriptContent
- `PUT` `/applications/{applicationName}/datasources/{datasourceName}` - Update Application Datasource
- `PUT` `/applications/{application}/databases/{database}/queries/rename` - Rename MDX report.
- `PUT` `/backup/settings` - saveSettings
- `PUT` `/cloudstorage/config` - Create or update object storage configuration
- `PUT` `/datasources/{datasourceName}` - Update Global Datasource
- `PUT` `/federatedoverdatasource/mapping` - createMappingLegacy
- `PUT` `/federatedoverdatasource/mapping/cr` - createMappingCR
- `PUT` `/federatedoverdatasource/mapping/denormalized` - createMappingDenormalized
- `PUT` `/federatedoverdatasource/mapping/denormalized/legacytest` - createMappingDenormalizedLegacyTest
- `PUT` `/odbc` - write_1
- `PUT` `/outline/{app}/{cube}/edit` - save
- `PUT` `/outline/{app}/{cube}/qedit` - save_1

Endpoints present as of 21.1, the oldest spec archived here, are not listed - there is nothing earlier to compare them against.
