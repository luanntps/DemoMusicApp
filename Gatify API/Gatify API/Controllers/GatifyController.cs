using Gatify_API.Models;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Gatify_API.Controllers
{
    public class GatifyController : ApiController
    {
        GatifyEntities gatifyEntities = new GatifyEntities();

        [HttpGet]
        public IHttpActionResult GetAllUser()
        {
            List<gatifyUser> list = gatifyEntities.gatifyUsers.ToList();
            return Ok(list);
        }
        [HttpGet]
        public IHttpActionResult Index(string id)

        {

            return Ok(id);

        }

        
        [HttpGet]
        public IHttpActionResult GetUserDetail(string email)
        {
            gatifyUser user=gatifyEntities.gatifyUsers.SingleOrDefault(p=>p.email.Equals(email));
            if (user == null)
            {
                return NotFound();
            }
            return Ok(user);
        }
        [HttpGet]
        public IHttpActionResult GetAllPlaylist(string email)
        {
            using(var context=new GatifyEntities())
            {
                var playlists = context.playlists.SqlQuery("Select *from playlists where email=@email", new SqlParameter("@email", email)).ToList<playlist>;
                return Ok(playlists);

            }
        }
        [HttpGet]
        public IHttpActionResult GetAllSong()
        {
            return Ok(gatifyEntities.songs.ToList());
        }

        [HttpPost]
        public IHttpActionResult CreateUser(gatifyUser u)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return Ok("Thêm sản phẩm không thành công");
                }
                gatifyEntities.gatifyUsers.Add(u);
                gatifyEntities.SaveChanges();
            }
            catch (Exception)
            {
                return Ok("Thêm sản phẩm không thành công");
            }
            return Ok("Thêm sản phẩm thành công");
        }
        [HttpPost]
        public IHttpActionResult UpdateProfilePic(string url, string email)
        {
            using (var context = new GatifyEntities())
            {
                var user = context.gatifyUsers.SingleOrDefault(x => x.email.Equals(email));
                user.url_user_pic = url;
                context.SaveChanges();
                return Ok();
            }
        }
        [HttpPost]
        public IHttpActionResult CreatePlaylist(playlist pl)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }
                gatifyEntities.playlists.Add(pl);
                gatifyEntities.SaveChanges();
            }
            catch (Exception)
            {
                return BadRequest(ModelState);
            }
            return Ok();
        }
        [HttpPost]
        public IHttpActionResult DeletePlaylist(playlist pl)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }
                gatifyEntities.playlists.Remove(pl);
                gatifyEntities.SaveChanges();
            }
            catch (Exception)
            {
                return BadRequest(ModelState);
            }
            return Ok();
        }
        [HttpPost]
        public IHttpActionResult AddSongToPlaylist(int playlistId, int songId)
        {
            using (var context = new GatifyEntities())
            {
                playlistManager playlist_Manager = new playlistManager();
                playlist_Manager.id_playlist = playlistId;
                playlist_Manager.id_song = songId;
                context.playlistManagers.Add(playlist_Manager);
                context.SaveChanges();
                return Ok();
            }
        }
        [HttpPost]
        public IHttpActionResult DeleteSongFromlist(int playlistId, int songId)
        {
            using (var context = new GatifyEntities())
            {
                playlistManager playlist_Manager = new playlistManager();
                playlist_Manager.id_playlist = playlistId;
                playlist_Manager.id_song = songId;
                context.playlistManagers.Remove(playlist_Manager);
                context.SaveChanges();
                return Ok();
            }
        }
        [HttpPost]
        public IHttpActionResult UpdateVip(String email)
        {
            using (var context = new GatifyEntities())
            {
                var user = context.gatifyUsers.SingleOrDefault(x => x.email.Equals(email));
                user.isVip = "yes";
                user.expried_date = DateTime.Now.AddMonths(1);
                context.SaveChanges();
                return Ok();
            }
        }
        [HttpGet]
        public IHttpActionResult GetAllGenre()
        {
            return Ok(gatifyEntities.genres.ToList());
        }
    }
}
