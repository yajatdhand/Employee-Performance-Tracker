-- Seed data for testing

-- Employees
INSERT INTO employee (name, department, role, joining_date, email) VALUES
    ('John Doe', 'Engineering', 'Senior Engineer', '2023-01-15', 'john.doe@company.com'),
    ('Jane Smith', 'Engineering', 'Staff Engineer', '2022-06-01', 'jane.smith@company.com'),
    ('Bob Wilson', 'Marketing', 'Marketing Manager', '2021-03-10', 'bob.wilson@company.com');

-- Review Cycles
INSERT INTO review_cycle (name, start_date, end_date) VALUES
    ('Q1 2025', '2025-01-01', '2025-03-31'),
    ('Q2 2025', '2025-04-01', '2025-06-30');

-- Performance Reviews
INSERT INTO performance_review (employee_id, review_cycle_id, rating, reviewer_notes, submitted_at) VALUES
    (1, 1, 4, 'Strong technical contributions', CURRENT_TIMESTAMP),
    (2, 1, 5, 'Exceptional leadership and output', CURRENT_TIMESTAMP),
    (3, 1, 3, 'Meeting expectations, room to grow', CURRENT_TIMESTAMP),
    (1, 2, 5, 'Outstanding quarter, led migration project', CURRENT_TIMESTAMP),
    (2, 2, 4, 'Consistent high performer', CURRENT_TIMESTAMP);

-- Goals
INSERT INTO goal (employee_id, review_cycle_id, title, status) VALUES
    (1, 1, 'Complete microservices migration', 'completed'),
    (1, 1, 'Reduce API latency by 20%', 'completed'),
    (1, 1, 'Mentor 2 junior developers', 'pending'),
    (2, 1, 'Implement CI/CD pipeline', 'completed'),
    (2, 1, 'Write technical design docs', 'missed'),
    (3, 1, 'Launch Q1 campaign', 'completed'),
    (3, 1, 'Increase social engagement by 15%', 'missed');
