namespace Gatify_API.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("sosteam.playlist")]
    public partial class playlist
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public playlist()
        {
            playlistManagers = new HashSet<playlistManager>();
        }

        public int id { get; set; }

        [Required]
        [StringLength(255)]
        public string url_playlist_pic { get; set; }

        [Required]
        [StringLength(255)]
        public string email { get; set; }

        public virtual gatifyUser gatifyUser { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<playlistManager> playlistManagers { get; set; }
    }
}
