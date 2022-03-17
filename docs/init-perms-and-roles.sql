insert into perms (name, description) values ('sponsor-property', 'Can sponsor a property'), ('can-authorize-realtor', 'Can authorize a realtor');
insert into roles (name, description) values ('admin', 'Administrator'), ('realtor', 'Realtor');
insert into roles_perms (role_id, perm_id) values ((select id from roles where name = 'admin'), (select id from perms where name = 'can-authorize-realtor'));
insert into roles_perms (role_id, perm_id) values ((select id from roles where name = 'realtor'), (select id from perms where name = 'sponsor-property'));