namespace Gatify_API.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("sosteam.song")]
    public partial class song
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public song()
        {
            playlistManagers = new HashSet<playlistManager>();
            songManagers = new HashSet<songManager>();
        }

        public int id { get; set; }

        [Required]
        [StringLength(255)]
        public string song_name { get; set; }

        public int view_count { get; set; }

        [Required]
        [StringLength(7000)]
        public string lyrics { get; set; }

        [Required]
        [StringLength(255)]
        public string url_song_pic { get; set; }

        [Required]
        [StringLength(255)]
        public string url_media { get; set; }

        public int genre_id { get; set; }

        public virtual genre genre { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<playlistManager> playlistManagers { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<songManager> songManagers { get; set; }
    }
}
