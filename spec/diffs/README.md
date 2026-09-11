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

Endpoints present as of 21.1, the oldest spec archived here, are not listed - there is nothing earlier to compare them against.
