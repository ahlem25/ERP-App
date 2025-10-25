-- Fix for Client.articles orphaned collection issue
-- This script removes any foreign key constraints that might be causing the issue

-- Check if there are any foreign key constraints from articles to client
-- and remove them if they exist
ALTER TABLE articles DROP FOREIGN KEY IF EXISTS fk_articles_client_id;
ALTER TABLE articles DROP COLUMN IF EXISTS client_id;

-- If the articles table has a client_id column, remove it
-- (This should already be done based on the Article entity)
