create table tactics_to_techniques
(
    tactic_id    integer
        constraint tactic_id_to_tactic
            references tactics,
    technique_id integer
        constraint unique_technique
            unique
        constraint technique_id_to_technique
            references main_techniques
);

INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 1);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 2);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 3);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 4);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 5);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 6);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 7);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 8);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 9);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 10);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 11);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 12);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 13);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 14);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 15);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 16);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 17);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 18);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 19);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (1, 20);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 21);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 22);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 23);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 24);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 25);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 26);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 27);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 28);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 29);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 30);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 31);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 32);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 33);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (2, 34);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 35);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 36);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 37);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 38);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 39);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 40);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 41);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 42);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 43);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 44);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 45);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 46);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 47);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 48);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 49);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (3, 50);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (4, 51);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (4, 52);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (4, 53);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (4, 54);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (4, 55);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (4, 56);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (4, 57);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 58);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 59);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 60);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 61);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 62);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 63);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 64);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 65);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 66);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 67);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 68);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 69);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (5, 70);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 71);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 72);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 73);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 74);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 75);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 76);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 77);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 78);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (6, 79);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 80);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 81);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 82);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 83);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 84);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 85);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 86);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 87);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 88);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 89);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 90);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 91);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 92);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 93);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 94);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 95);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 96);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 97);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 98);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 99);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 100);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 101);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 102);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 103);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 104);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 105);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 106);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 107);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (7, 108);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (8, 109);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (8, 110);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (8, 111);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (8, 112);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (8, 113);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (8, 114);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (8, 115);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (8, 116);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 117);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 118);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 119);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 120);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 121);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 122);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 123);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 124);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 125);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 126);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 127);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 128);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 129);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (9, 130);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 131);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 132);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 133);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 134);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 135);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 136);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 137);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 138);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 139);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 140);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 141);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 142);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 143);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 144);
INSERT INTO threat_and_sec_schema.tactics_to_techniques (tactic_id, technique_id)
VALUES (10, 145);
