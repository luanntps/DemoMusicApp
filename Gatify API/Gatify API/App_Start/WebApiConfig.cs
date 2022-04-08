using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Web.Mvc;

namespace Gatify_API
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Web API configuration and services
            var json = config.Formatters.JsonFormatter;
            json.SerializerSettings.PreserveReferencesHandling = Newtonsoft.Json.PreserveReferencesHandling.None;
            json.SerializerSettings.ReferenceLoopHandling
            = Newtonsoft.Json.ReferenceLoopHandling.Ignore;
            config.Formatters.Remove(config.Formatters.XmlFormatter);
            // Web API routes
            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "GetAllUser",     
                routeTemplate: "api/get-all-user",
                defaults: new { controller = "Gatify", action = "GetAllUser" }
            );

            config.Routes.MapHttpRoute(
                name: "GetUserDetail2",
                routeTemplate: "api/get-user-detail/{id}",
                defaults: new { controller = "Gatify", action = "GetUserDetail", id = UrlParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "Index",
                routeTemplate: "api/index/{id}",
                defaults: new { controller = "Gatify", action = "Index", id = UrlParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "GetAllPlaylist",
                routeTemplate: "api/get-all-playlist/{id}",
                defaults: new { controller = "Gatify", action = "GetAllPlaylist", id = UrlParameter.Optional }
            );

            config.Routes.MapHttpRoute(
                name: "GetAllSong",
                routeTemplate: "api/get-all-song",
                defaults: new { controller = "Gatify", action = "GetAllSong" }
            );

            config.Routes.MapHttpRoute(
                name: "CreateUser",
                routeTemplate: "api/create-user",
                defaults: new { controller = "Gatify", action = "CreateUser" }
            );

            config.Routes.MapHttpRoute(
                name: "UpdateProfilePic",
                routeTemplate: "api/update-profile-pic",
                defaults: new { controller = "Gatify", action = "UpdateProfilePic" }
            );

            config.Routes.MapHttpRoute(
                name: "UpdateVip",
                routeTemplate: "api/update-vip",
                defaults: new { controller = "Gatify", action = "UpdateVip" }
            );

            config.Routes.MapHttpRoute(
                name: "CreatePlaylist",
                routeTemplate: "api/create-playlist",
                defaults: new { controller = "Gatify", action = "CreatePlaylist" }
            );

            config.Routes.MapHttpRoute(
                name: "DeletePlaylist",
                routeTemplate: "api/delete-playlist",
                defaults: new { controller = "Gatify", action = "DeletePlaylist" }
            );

            config.Routes.MapHttpRoute(
                name: "AddSongToPlaylist",
                routeTemplate: "api/add-song-to-playlist",
                defaults: new { controller = "Gatify", action = "AddSongToPlaylist" }
            );

            config.Routes.MapHttpRoute(
                name: "DeleteSongFromPlaylist",
                routeTemplate: "api/delete-song-from-playlist",
                defaults: new { controller = "Gatify", action = "DeleteSongFromPlaylist" }
            );

            config.Routes.MapHttpRoute(
                name: "GetAllGenre",
                routeTemplate: "api/get-all-genre",
                defaults: new { controller = "Gatify", action = "GetAllGenre" }
            );

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );
        }
    }
}
