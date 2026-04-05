# Employee Performance Tracker — System Design

1. How would you scale this system if it needed to support 500 concurrent managers running reports during performance season?
-First for vertical scaling, we would need to up the default tomcat thread pool size from the default 200 to 600, and the real bottleneck would again not just be the thread pool size but the I/O operations for DB, which multiple threads would be waiting on.
-Second, we definitely need to increase the DB connection pool size to hold more open connections (a number like 40) in a single instance, since HikariCP by default provides a connection pool of 10, and if 500 concurrent users are hitting our APIs then 500 users would be exhausting a pool of 10.

2. If a GET /cycles/{id}/summary query starts getting slow at 100k+ reviews, what would you do?
-First, we would need to verify whether or not the query planner is doing sequential scans instead of using the index on review_cycle_id, by running EXPLAIN ANALYZE on the query.
-Second, we need caching in a real production system — for the current implementation I have used local caching via HashMap, but we would need Redis to handle such number of requests. Caching the cycle summary and evicting it on new review submissions will eliminate DB hits for the vast majority of reads during peak season.

3. Where would you add caching, and what would you cache?
-We can cache the response of 'GET /cycles/{id}/summary' with a 10-minute TTL — this is the most expensive query and the one all managers will hit simultaneously. The moment POST /reviews is called for that cycle, so managers never see stale summary data after a new submission. In production this would be backed by Redis. For this submission, I have used Spring's simple cache, which behaves identically in terms of invalidation logic.