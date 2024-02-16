
namespace Controllers;

using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Models;
using Data;

[ApiController]
[Route("/tutorials")]
public class TutorialsController : ControllerBase {
    
    private readonly DatabaseContext _context;
    
    public TutorialsController(DatabaseContext context) {
        _context = context;
    }
    
    [HttpGet]
    public async Task<ActionResult<IEnumerable<Tutorials>>> GetAllTutorialss() {
        return await _context.Tutorialss.ToListAsync();
    }
    
    [HttpGet("{id}")]
    public async Task<ActionResult<Tutorials>> GetTutorialsById(int id) {
        var tutorials = await _context.Tutorialss.FirstOrDefaultAsync(t => t.Id == id);
        
        if(tutorials == null) {
            return NotFound();
        }
        
        return tutorials;
    }
    
    [HttpPost]
    public async Task<ActionResult<Tutorials>> CreateTutorials(Tutorials tutorials) {
        _context.Tutorialss.Add(tutorials);
        await _context.SaveChangesAsync();
        
        return CreatedAtAction(nameof(GetTutorialsById), new { id = tutorials.Id }, tutorials);
    }
    
    [HttpPut("{id}")]
    public async Task<IActionResult> UpdateTutorials(int id, Tutorials tutorials) {
        if (id != tutorials.Id)
        {
            return BadRequest();
        }
        
        _context.Entry(tutorials).State = EntityState.Modified;
        
        try
        {
            await _context.SaveChangesAsync();
        }
        catch(DbUpdateConcurrencyException)
        {
            throw;
        }
        
        return NoContent();
    }
    
    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteTutorials(int id) {
        var tutorials = await _context.Tutorialss.FindAsync(id);
        if (tutorials == null)
        {
            return NotFound();
        }
        
        _context.Tutorialss.Remove(tutorials);
        await _context.SaveChangesAsync();
        
        return NoContent();
    }
    
}
