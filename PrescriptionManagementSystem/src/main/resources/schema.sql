CREATE TABLE patient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    telephone_number VARCHAR(20),
    date_of_birth DATE,
    insurance_provider VARCHAR(100),
    insurance_policy_number VARCHAR(100)
);

CREATE TABLE physician (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    telephone_number VARCHAR(20)
);

CREATE TABLE medication (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    unit VARCHAR(20),
    side_effects VARCHAR(255)
);

CREATE TABLE prescription (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_of_issue DATE,
    expiration_date DATE,
    number_of_refills_authorized INT,
    units_per_refill VARCHAR(20),
    allow_generic_substitute BOOLEAN,
    patient_id BIGINT,
    physician_id BIGINT,
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patient(id),
    CONSTRAINT fk_physician FOREIGN KEY (physician_id) REFERENCES physician(id)
);

CREATE TABLE prescription_medication (
    prescription_id BIGINT,
    medication_id BIGINT,
    PRIMARY KEY (prescription_id, medication_id),
    CONSTRAINT fk_prescription FOREIGN KEY (prescription_id) REFERENCES prescription(id),
    CONSTRAINT fk_medication FOREIGN KEY (medication_id) REFERENCES medication(id)
);
