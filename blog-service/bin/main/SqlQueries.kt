package com.metarhythm.honely.blogservice

data class SqlQueries(
  val createPostsTable : String = "create table if not exists posts (id serial primary key, title text not null, "+
          "subtitle text, post_date date not null, slug text not null, body text, image_filename text, unique(post_date, slug))",
  val posts : String = "select * from posts order by post_date desc limit $1 offset $2",
  val totalPosts : String = "select count(*) from posts",
  val post : String = "select * from posts where post_date = $1 and slug = $2",
  val insertPost : String = "insert into posts (title, subtitle, post_date, slug, body, image_filename) values ($1, $2, $3, $4, $5, $6)  on conflict on constraint posts_post_date_slug_key do update set title = $1, subtitle = $2, post_date = $3, slug = $4, body = $5, image_filename = $6 returning id, title, subtitle, post_date, slug",
  val deletePost : String = "delete from posts where id = $1",
  val updatePost : String = "update posts set title = $1, subtitle = $2, post_date = $3, slug = $4, body = $5, image_filename = $6 where id = $7 returning id, title, post_date, slug",
  val getPostById : String = "select * from posts where id = $1"
)
