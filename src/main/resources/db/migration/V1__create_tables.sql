CREATE TABLE transactions (
    reference VARCHAR(255) NOT NULL PRIMARY KEY,
    account_iban VARCHAR(255) NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    fee NUMERIC(19,2),
    created_date TIMESTAMP,
    description VARCHAR(255)
);

CREATE TABLE accounts (
    iban VARCHAR(255) NOT NULL PRIMARY KEY,
    balance NUMERIC(19,2) NOT NULL
)