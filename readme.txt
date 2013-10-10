Joseph Riley

Here are the patterns I used in searching for temporal patterns in the class bio text.

1. String month = "((January|February|March|April|May|June|July|August|September|October|November|December)(\\b))";
The first pattern will search for exact full month names ending on the word boundary.
This has the lowest possibility for false positives.

2. String monthshort = "((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sept|Oct|Nov|Dec)(\\b))";
The second pattern searches for month abbreviations.
This has low potential for false positives outside of proper names (Jan, May).

3. String descriptive = "(((|n)ext|(|l)ast|(|t)his|previous)\\s(year|quarter|month)(\\b))";
The third pattern looks for descriptive time terms, such as "This year", "last month", etc.
This has decent potential for false positives.  Line 5 on the output is a false positive (this year-long) but I am unable to correct for this (at least at current ideas) based on the approach we took with the tokenization.  The construction (|t)his, for example, was used to find triggers at the start of the sentence.  We already look for an upper-case character so this ignores the upper-case character in the trigger pattern and moves to his.

4. String yearnum = "(((|i)n|(|d)uring|(|o)ver|(|b)y)\\s((19)|(20))([0-9]{2}))";
The fourth pattern searches for a 4 digit number starting with 19 or 20 followed by space or punctuation and preceded by words in, during, and over which help limit the scope of that 4 digit number.
This still has potential for false positives and false negatives.


5. String seasons = "(((|i)n|(|d)uring|(|o)ver|(|b)y)\\s(the)\\s((S|s)ummer|(W|w)inter|(F|f)all|(S|s)pring))";
This pattern searches for seasons preceded again by certain descriptive words that imply a temporal event.  This certainly has potential for false positives.

6. String descSeasons = "(((|n)ext|(|l)ast|(|t)his|previous)\\s((S|s)ummer|(W|w)inter|(F|f)all|(S|s)pring))";
This pattern may be a bit more directed than the previous seasons and searches for a different set of preceding words that also have a temporal context.

A big area that I didn't explore was writing out expressions for absolute date or time formats.  Scanning through our document didn't reveal any so I figured I wouldn't fill space with them.  However, I can see that there would be multiple different formats that would need to be covered.  Looking at one of the database documentations (postgres) would lead me to write a bunch of different patterns to account for the various forms that dates and datetimes can come in.

