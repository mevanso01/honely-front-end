package com.metarhythm.honely.publicapi

data class SqlQueries(
  val createFavoritesTable : String = "create table if not exists favorites (id serial primary key, user_id integer, " +
          "address text, fips text, apn text, created_at timestamp with time zone default now(), unique(user_id, fips, apn))",
  val getFavorites : String = "select * from favorites where user_id = $1 order by id",
  val insertFavorite : String = "insert into favorites (user_id, address, fips, apn, created_at) values ($1, $2, $3, $4, now())  on conflict on constraint favorites_user_id_fips_apn_key do update set user_id = $1, address = $2, fips = $3, apn = $4 returning id, user_id, address, fips, apn",
  val deleteFavorite : String = "delete from favorites where user_id = $1 and fips = $2 and apn = $3",
  val getUser: String = "select * from users where email = $1 limit 1",
  val getUserProfile: String = "select id, first_name, last_name, email, cell_phone, work_phone, address_1, address_2, city, state, zip from users where email = $1 limit 1",
  val createSavedSearchesTable : String = "create table if not exists saved_searches (id serial primary key, user_id integer, "+
          "search text, created_at timestamp with time zone default now())",
  val getSavedSearches : String = "select * from saved_searches where user_id = $1 order by id",
  val insertSavedSearch : String = "insert into saved_searches (user_id, search, created_at) values ($1, $2, now()) returning id, user_id, search",
  val deleteSavedSearch : String = "delete from saved_searches where user_id = $1 and id = $2",
  val addRealtorFields : String = "alter table users add column if not exists realtor_id text;" +
          "alter table users add column if not exists realtor_status text;" +
          "alter table users add column if not exists realtor_uuid uuid;",
  val setRealtorFields : String = "update users set realtor_id = $1, realtor_status = $2, realtor_uuid = $3 where email = $4",
  val getRealtorByUuid : String = "select id, first_name, last_name, email, realtor_id, realtor_status from users where realtor_uuid = $1 limit 1",
  val confirmRealtor : String = "update users set realtor_status = 'confirmed' where id = $1",
  val setRelatorRole : String = "insert into users_roles (user_id, role_id) values ($1, (select id from roles where name = 'realtor' limit 1))",
  val createSponsoredListings : String = "create table if not exists sponsored_listings (id serial primary key, user_id integer, "+
          "fips text, apn text, created_at timestamp with time zone default now(), unique(user_id, fips, apn), description text)",
  val createListingImages : String = "create table if not exists listing_images (id serial primary key, sponsored_listing_id integer, file_name text, order_by integer default 1)",
  val insertSponsorListing : String = "insert into sponsored_listings (fips, apn, user_id) values ($1, $2, (select id from users where email = $3 limit 1))",
  val getSponsoredListing : String = "select sl.*, " +
          "ARRAY(select file_name from listing_images where sponsored_listing_id = sl.id order by id) as images " +
          "from sponsored_listings sl where sl.fips = $1 and sl.apn = $2",
  val updateSponsoredListingDescription : String = "update sponsored_listings set description = $1 where id = $2",
  val insertListingImage : String = "insert into listing_images (sponsored_listing_id, file_name) values ($1, $2)",
  val getMySponsoredListings : String = "select sponsored_listings.* from sponsored_listings " +
          "inner join users on user_id = users.id where email = $1",
  val createInsightsSignUps : String = "create table if not exists insights_sign_ups (id serial primary key, email text not null, created_at timestamp with time zone, unique(email))",
  val insertInsightsSignUp : String = "insert into insights_sign_ups (email, created_at) values ($1, now())  on conflict on constraint insights_sign_ups_email_key do update set email = $1 returning id, email, created_at",
  val setUserLastLoggedIn : String = "update users set last_logged_in_at = now() where id = $1",
  val updateUser : String = "update users set first_name = $1, last_name = $2, work_phone = $3, cell_phone = $4, "+
          "address_1 = $5, address_2 = $6, city = $7, state = $8, zip = $9 where id = $10"
)
