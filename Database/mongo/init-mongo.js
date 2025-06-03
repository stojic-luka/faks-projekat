db = db.getSiblingDB("recipes");

db.createCollection("messages");
db.createCollection("favoriteRecipes");
db.createCollection("recipes");
db.createCollection("users");

db.createUser({
  user: "user",
  pwd: "password",
  roles: [{ role: "readWrite", db: "recipes" }],
});
