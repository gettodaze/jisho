## jisho

- Android App Based on python/jisho.py
- Uses jisho.org API to take input query and return results.
- Allows the user to not only query, but also store lists of words, and export those lists.
- python helpstring:

```
def helpstring(self):
        return f"""John's Jisho.
Current list: {self.cur_list}
Current json: {self.json_path}
Type in a keyword or a special argument. Arguments
Q: quit
.H: helpstring
.CL <list>: change list to <list>
.NL <list>: create new list <list>
.SL: show current list
.EL: export list
M: more definitions
letter+number combo: save word-sense pair to list."""
```
