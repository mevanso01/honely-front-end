create table if not exists posts (id serial primary key, title text not null, post_date date not null, slug text not null, body text, unique(post_date, slug))
