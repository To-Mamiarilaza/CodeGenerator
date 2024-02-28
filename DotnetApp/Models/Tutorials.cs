
namespace Models;

using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;


[Table("tutorials")]
public class Tutorials {
    /// Field
    [Key]
    [Column("id")]
    public int Id { get; set; }
    
    [Column("title")]
    public string Title { get; set; }
    
    [Column("description")]
    public string Description { get; set; }
    
    [Column("level")]
    public int Level { get; set; }
    
    [Column("published")]
    public bool Published { get; set; }
    
    
    
    /// Constructor
    public Tutorials(){}
    
    
    /// Getter and setter
    
}
