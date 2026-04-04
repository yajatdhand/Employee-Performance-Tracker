CREATE TABLE employee (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    department    VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL,
    joining_date  DATE         NOT NULL,
    email         VARCHAR(255) NOT NULL,
    CONSTRAINT uq_employee_email UNIQUE (email)
);

CREATE TABLE review_cycle (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    start_date DATE         NOT NULL,
    end_date   DATE         NOT NULL,
    CONSTRAINT uq_cycle_name UNIQUE (name),
    CONSTRAINT chk_cycle_dates CHECK (end_date > start_date)
);

CREATE TABLE performance_review (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id      BIGINT       NOT NULL,
    review_cycle_id  BIGINT       NOT NULL,
    rating           INT          NOT NULL,
    reviewer_notes   TEXT,
    submitted_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pr_employee      FOREIGN KEY (employee_id)     REFERENCES employee(id),
    CONSTRAINT fk_pr_cycle         FOREIGN KEY (review_cycle_id) REFERENCES review_cycle(id),
    CONSTRAINT chk_rating          CHECK (rating >= 1 AND rating <= 5)
);

CREATE TABLE goal (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id      BIGINT       NOT NULL,
    review_cycle_id  BIGINT       NOT NULL,
    title            VARCHAR(255) NOT NULL,
    status           VARCHAR(20)  NOT NULL DEFAULT 'pending',
    CONSTRAINT fk_goal_employee    FOREIGN KEY (employee_id)     REFERENCES employee(id),
    CONSTRAINT fk_goal_cycle       FOREIGN KEY (review_cycle_id) REFERENCES review_cycle(id),
    CONSTRAINT chk_goal_status     CHECK (status IN ('pending', 'completed', 'missed'))
);

-- Indexes for query performance
CREATE INDEX idx_pr_employee_id    ON performance_review(employee_id);
CREATE INDEX idx_pr_cycle_id       ON performance_review(review_cycle_id);
CREATE INDEX idx_goal_cycle_status ON goal(review_cycle_id, status);
CREATE INDEX idx_emp_department    ON employee(department);
