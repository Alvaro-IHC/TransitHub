insert into users (id, username, email, password, name, last_name)
    values ('ea09f0f0-6ab1-4cfd-971e-5002fa6f2aa9', 'admin', 'admin@th.com', '$2a$10$WYSGTQ7b0SZsCvbuCrWyR.z9QnJ1e5nvxc58GAY8Ljz9.nxGDQyWS', 'admin', 'admin')
    on conflict (id) do nothing;

insert into admins (id) values ('ea09f0f0-6ab1-4cfd-971e-5002fa6f2aa9') on conflict (id) do nothing;