# Employee Performance Tracker — System Design

1. How would you scale this system if it needed to support 500 concurrent managers running reports during performance season?
First for vertical scaling, we would need to up the default tomcat thread pool size from the default 200, and the real bottleneck would again not just be the thread pool size but the I/O operations for DB, which multiple threads would be waiting on.
Second, we definitely need to increase the DB connection pool size to hold more open connections (a number like 40) in a single instance.

2. If a GET /cycles/{id}/summary query starts getting slow at 100k+ reviews, what would you do?
The absolute first thing we need in a real production level system is proper caching, for this I have used local caching via HashMap but we would need Redis to be able to handle such number of requests, this would save multiple DB hits. We can store summary as cache and return response from Redis instead of our DB.
Second is, we would need more indexing maybe composite to reach somewhere close to an index only scan.

3. Where would you add caching, and what would you cache?
We can definitely cache Cycle summary and Employee reviews whenever new reviews are submitted so that we respond quickly and keep our app alive.