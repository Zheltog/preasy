# Description
preasy is the simple short-term file storage.\
preasy might be used if you need to share some file (e.g. PowerPoint presentation) but don't want to use USB-drive nor sign in your Google Drive from the other device.\
Just upload your file and remember short ID to type it later.
 
# REST API

1 **Check if alive**\
Method: GET\
Path: check

2 **Save new file**\
Method: POST\
Path: save\
Requires: form-data with\
a) password: string or null\
b) file: file attachment\
Returns: ID of new save

3 **Get file**\
Method: POST\
Path: get\
Requires: JSON with\
a) ID: string\
b) password: string (if was defined) or null (if wasn't)\
Returns: file content

4 **Delete file**\
Method: DELETE\
Path delete/{id}