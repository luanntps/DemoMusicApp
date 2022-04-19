using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity;
using System.Linq;

namespace Gatify_API.Models
{
    public partial class GatifyEntities : DbContext
    {
        public GatifyEntities()
            : base("name=GatifyEntities5")
        {
        }

        public virtual DbSet<artist> artists { get; set; }
        public virtual DbSet<comment> comments { get; set; }
        public virtual DbSet<gatifyUser> gatifyUsers { get; set; }
        public virtual DbSet<playlist> playlists { get; set; }
        public virtual DbSet<playlistManager> playlistManagers { get; set; }
        public virtual DbSet<song> songs { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<artist>()
                .Property(e => e.url_profile_pic)
                .IsUnicode(false);

            modelBuilder.Entity<artist>()
                .HasMany(e => e.songs)
                .WithRequired(e => e.artist)
                .HasForeignKey(e => e.id_artist)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<comment>()
                .Property(e => e.email)
                .IsUnicode(false);

            modelBuilder.Entity<comment>()
                .Property(e => e.url_user_pic)
                .IsUnicode(false);

            modelBuilder.Entity<gatifyUser>()
                .Property(e => e.email)
                .IsUnicode(false);

            modelBuilder.Entity<gatifyUser>()
                .Property(e => e.isVip)
                .IsUnicode(false);

            modelBuilder.Entity<gatifyUser>()
                .Property(e => e.url_user_pic)
                .IsUnicode(false);

            modelBuilder.Entity<gatifyUser>()
                .HasMany(e => e.comments)
                .WithRequired(e => e.gatifyUser)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<gatifyUser>()
                .HasMany(e => e.playlists)
                .WithRequired(e => e.gatifyUser)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<playlist>()
                .Property(e => e.url_playlist_pic)
                .IsUnicode(false);

            modelBuilder.Entity<playlist>()
                .Property(e => e.email)
                .IsUnicode(false);

            modelBuilder.Entity<playlist>()
                .HasMany(e => e.playlistManagers)
                .WithRequired(e => e.playlist)
                .HasForeignKey(e => e.id_playlist)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<song>()
                .Property(e => e.url_song_pic)
                .IsUnicode(false);

            modelBuilder.Entity<song>()
                .Property(e => e.url_media)
                .IsUnicode(false);

            modelBuilder.Entity<song>()
                .HasMany(e => e.comments)
                .WithRequired(e => e.song)
                .HasForeignKey(e => e.id_song)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<song>()
                .HasMany(e => e.playlistManagers)
                .WithRequired(e => e.song)
                .HasForeignKey(e => e.id_song)
                .WillCascadeOnDelete(false);
        }
    }
}
