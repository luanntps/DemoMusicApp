namespace Gatify_API.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("sosteam.playlistManager")]
    public partial class playlistManager
    {
        [Key]
        public int no { get; set; }

        public int id_playlist { get; set; }

        public int id_song { get; set; }

        public virtual playlist playlist { get; set; }

        public virtual song song { get; set; }
    }
}
