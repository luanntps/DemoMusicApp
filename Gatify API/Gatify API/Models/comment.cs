namespace Gatify_API.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("sosteam.comment")]
    public partial class comment
    {
        [Key]
        public int id_comment { get; set; }

        [Required]
        [StringLength(3000)]
        public string content { get; set; }

        [Required]
        [StringLength(255)]
        public string email { get; set; }

        public int id_song { get; set; }

        public virtual gatifyUser gatifyUser { get; set; }

        public virtual song song { get; set; }
    }
}
