INSERT INTO patient (id, name, telephone_number, date_of_birth, insurance_provider, insurance_policy_number)
VALUES (1, 'John Doe', '555-1234', '1985-05-15', 'HealthInsuranceCorp', 'H12345');

INSERT INTO physician (id, name, telephone_number)
VALUES (1, 'Dr. Smith', '555-6789');

INSERT INTO medication (id, name, unit, side_effects)
VALUES (1, 'Amoxicillin', '500mg', 'Nausea, headache');

INSERT INTO prescription (id, date_of_issue, expiration_date, number_of_refills_authorized, units_per_refill, allow_generic_substitute, patient_id, physician_id)
VALUES (1, '2024-01-01', '2024-12-31', 2, '30', true, 1, 1);

INSERT INTO prescription_medication (prescription_id, medication_id)
VALUES (1, 1);
